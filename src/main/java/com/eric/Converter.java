package com.eric;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.beust.jcommander.Parameter;

public class Converter extends Command {

	private static final String[] NFO_EXTENSIONS = { "nfo", "NFO" };
	private static final String[] VIDEO_EXTENSIONS = { "mkv", "avi", "iso", "m2ts" };

	@Parameter(description = "directory1 <directory2 <directory3...>>")
	private List<String> dirs = new ArrayList<String>();

	@Parameter(names = "-pretty", description = "Displays output with indentation.")
	private boolean prettyPrint;

	@Parameter(names = "-thumb-dir", description = "Specifies a directory to download all poster "
			+ "artwork to.  If nothing is specified, then no " + "posters are downloaded.")
	private String downloadThumbPath;

	@Parameter(names = "-thumb-height", description = "The max height of the thumbnail image.  Only used if "
			+ "-thumb-dir is set.")
	private int thumbHeight = 300;

	/**
	 * @param nfo
	 *            the nfo file
	 * @return the File representing the actual video file, or null if it could
	 *         not be found.
	 */
	private File video(File nfo) {
		File f = null;

		for (String ext : VIDEO_EXTENSIONS) {
			f = new File(FilenameUtils.removeExtension(nfo.getAbsolutePath()) + '.' + ext);
			if (f.exists() && f.isFile()) {
				return f;
			}
		}

		return null;
	}

	private JSONObject nfo(File nfo) throws IOException {
		String contents = FileUtils.readFileToString(nfo, "UTF-8");

		JSONObject retVal = XML.toJSONObject(contents);
		if (retVal.has("movie")) {
			retVal = retVal.getJSONObject("movie");
			retVal.put("location", nfo.getAbsolutePath());

			File video = video(nfo);
			if (video != null) {
				retVal.put("updated", new Date(video.lastModified()));
			} else {
				err("Unable to find corresponding video for %s", nfo.getAbsolutePath());
				retVal.put("updated", new Date(nfo.lastModified()));
			}

			if (downloadThumbPath != null) {
				String p = retVal.get("title").toString().replaceAll(" ", "_").replaceAll("\\W", "").toLowerCase()
						+ ".jpg";
				downloadThumb(retVal.getString("thumb"), p);
				retVal.put("localThumb", p);
			}

			return retVal;
		}

		err("Unable to parse NFO file %s.", nfo.getAbsolutePath());
		return null;
	}

	private void downloadThumb(String from, String filename) {
		File f = new File(downloadThumbPath, filename);

		if (!f.exists()) {
			f.getParentFile().mkdirs();
			try {
				Thumbnails.of(new URL(from)).height(thumbHeight).toFile(f);
				verbose("Downloaded poster to %s.", f.getAbsolutePath());
			} catch (Exception e) {
				err("Unable to download poster from " + from + ", to " + f.getAbsolutePath(), e);
			}
		}
	}

	private JSONArray parse() throws JSONException, IOException {
		JSONObject movies = new JSONObject();

		for (String loc : dirs) {
			for (File file : FileUtils.listFiles(new File(loc), NFO_EXTENSIONS, true)) {
				if (!file.isHidden() && file.length() != 0L) {
					movies.accumulate("movies", nfo(file));
				}
			}
		}

		if (!movies.has("movies")) {
			return new JSONArray();
		}

		return movies.getJSONArray("movies");
	}

	@Override
	public void run() throws JSONException, IOException {
		if (prettyPrint) {
			out(parse().toString(3));
		} else {
			out(parse().toString());
		}
	}

	@Override
	protected String getProgramName() {
		return "nfojson";
	}

	@Override
	protected void validate(Collection<String> messages) {
		if (dirs.isEmpty()) {
			messages.add("At least one directory must be specified.");
		}
	}

	public static void main(String[] args) {
		Command.main(new Converter(), args);
	}
}
