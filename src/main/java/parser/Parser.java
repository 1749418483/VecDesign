package parser;

import behaviour.Behaviour;
import command.Command;
import context.Context;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Map;

public class Parser {

    private static GraphicsContext context;



    public static void setContext(GraphicsContext context) {
        Parser.context = context;
    }



    public static void parse(String keyword, String...args){
        System.out.println(keyword + " " + String.join(" ", args));
        if (context == null) throw new IllegalStateException("No current context");
        Context.getCommandByName(keyword).draw(context, args);
    }
    public static void parseAndAdd(String keyword, String...args){
        parse(keyword, args);
        Context.getCurrentCommands().add(keyword + " " + String.join(" ", args));
    }
}
