package main.java.com.vectorforce.compiler;

public class Context {
    private static final String NEW_LINE = "\n\t\t";
    private static final String SEMICOLON = ";";

    private static String context = "";
    private static String methods = "";

    private static boolean contextStream = true;

    public static void appendNewString(String string) {
        if(contextStream) {
            context += NEW_LINE + string;
        } else {
            methods += NEW_LINE + string;
        }
    }

    public static void appendCurrentString(String string) {
        if(contextStream) {
            context += string;
        } else {
            methods += string;
        }
    }

    public static void completeLine() {
        if (contextStream) {
            context += SEMICOLON;
        } else {
            methods += SEMICOLON;
        }
    }

    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        Context.context = context;
    }

    public static String getMethods() {
        return methods;
    }

    public static void setMethods(String methods) {
        Context.methods = methods;
    }

    public static boolean isContextStream() {
        return contextStream;
    }

    public static void setContextStream(boolean contextStream) {
        Context.contextStream = contextStream;
    }
}
