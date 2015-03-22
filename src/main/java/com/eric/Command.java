package com.eric;

import java.util.ArrayList;
import java.util.Collection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public abstract class Command {

    @Parameter(names = {"-h", "--help"}, help = true, description = "Displays this help message.")
    private boolean help;

    @Parameter(names = {"-v", "--verbose"}, description = "Displays more output.")
    private boolean verbose;

    @Parameter(names = {"-d", "--debug"}, hidden = true)
    private boolean debug;

    protected void validate(Collection<String> messages) {
    }

    protected abstract String getProgramName();

    protected abstract void run() throws Exception;

    protected void out(String msg) {
        System.out.println(msg);
    }

    protected void info(String message) {
        if (verbose) {
            out(message);
        }
    }

    protected void warn(String message, Exception ex) {
        warn(message);

        if (debug) {
            ex.printStackTrace();
        }
    }

    protected void warn(String message) {
        System.err.println(message);
    }

    public static void main(Command cmd, String... args) {
        JCommander jc = new JCommander(cmd);

        try {
            jc.setProgramName(cmd.getProgramName());
            jc.parse(args);

            if (cmd.help) {
                jc.usage();
            } else {
                Collection<String> messages = new ArrayList<String>();
                cmd.validate(messages);
                if (messages.isEmpty()) {
                    cmd.run();
                } else {
                    for (String m : messages) {
                        cmd.warn(m);
                    }

                    jc.usage();
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            jc.usage();

            if (cmd.debug) {
                e.printStackTrace();
            }

            System.exit(2);
        }
    }
}
