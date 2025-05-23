package com.example.movies_api.command.user;


import com.example.movies_api.command.LogCommand;
import com.example.movies_api.logger.CommandLogger;
import com.example.movies_api.auth.AuthenticationRequest;
import com.example.movies_api.logger.FileLogWriter;


//2/3 command pattern - the command is issued on the user authenticate either via JSON or XML
//[previously] that file did not exist
public class LogUserLoginCommand implements LogCommand {
    private final String email;

    // Konstruktor dla JSON
    public LogUserLoginCommand(AuthenticationRequest request) {
        this.email = request.getEmail();
    }

    // Konstruktor dla XML
    public LogUserLoginCommand(String xmlRequest) {
        this.email = extractEmailFromXml(xmlRequest);
    }


    @Override
    public void execute(FileLogWriter writer) {
        writer.writeToLog("users.log", "Zalogowano użytkownika: " + email);
    }





    // Prosty parser XML (dla testowych XML typu <email>xxx</email>)
    private String extractEmailFromXml(String xml) {
        try {
            int start = xml.indexOf("<email>") + 7;
            int end = xml.indexOf("</email>");
            if (start >= 7 && end > start) {
                return xml.substring(start, end);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "nieznany_uzytkownik";
    }
}
