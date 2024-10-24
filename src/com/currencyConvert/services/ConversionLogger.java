package com.currencyConvert.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionLogger {
    public static void saveConversionRecord (String sourceCurrency, String destinationCurrency, double amount, double convertedValue, double rate) {
        try (FileWriter fw = new FileWriter ("conversion_history.txt", true);
             PrintWriter pw = new PrintWriter (fw)) {
            LocalDateTime now = LocalDateTime.now ();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);

            pw.println ("Data/Hora: " + formattedDate);
            pw.printf ("De: %.2f %s -> %.2f %s (Taxa: %.4f)%n", amount, sourceCurrency, convertedValue, destinationCurrency, rate);
            pw.println ("------------------------------");
        } catch (IOException e) {
            System.out.println ("Erro ao gravar o histórico de conversão: " + e.getMessage());
        }
    }
}
