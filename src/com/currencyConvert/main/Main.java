package com.currencyConvert.main;

import com.currencyConvert.services.ConversionLogger;
import com.currencyConvert.services.CurrencyServices;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println (ANSI_BLUE + "======================" +
                    "\nConversor de Moedas" + ANSI_RESET);
            System.out.println(ANSI_YELLOW +
                    "1. USD -> EUR\n" +
                    "2. EUR -> USD\n" +
                    "3. USD -> BRL\n" +
                    "4. BRL -> USD\n" +
                    "5. EUR -> BRL\n" +
                    "6. BRL -> EUR\n" +
                    "7. Sair." + ANSI_RESET);
            System.out.print (ANSI_GREEN + "Escolha uma opção: " + ANSI_RESET);
            option = scanner.nextInt();

            if (option >= 1 && option <= 6) {
                System.out.print(ANSI_GREEN + "Quantidade a ser convertida: " + ANSI_RESET);
                double amount = scanner.nextDouble();

                String sourceCurrency = "";
                String destinationCurrency = "";

                switch (option) {
                    case 1:
                        sourceCurrency = "USD";
                        destinationCurrency = "EUR";
                        break;
                    case 2:
                        sourceCurrency = "EUR";
                        destinationCurrency = "USD";
                        break;
                    case 3:
                        sourceCurrency = "USD";
                        destinationCurrency = "BRL";
                        break;
                    case 4:
                        sourceCurrency = "BRL";
                        destinationCurrency = "USD";
                        break;
                    case 5:
                        sourceCurrency = "EUR";
                        destinationCurrency = "BRL";
                        break;
                    case 6:
                        sourceCurrency = "BRL";
                        destinationCurrency = "EUR";
                        break;
                }

                double rate = 0;
                try {
                    rate = CurrencyServices.getExchangeRate(sourceCurrency, destinationCurrency);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (rate > 0) {
                    double convertedValue = amount * rate;
                    System.out.printf(ANSI_BLUE + "Valor convertido: %.2f %s%n" + ANSI_RESET, convertedValue, destinationCurrency);

                    ConversionLogger.saveConversionRecord(sourceCurrency, destinationCurrency, amount, convertedValue, rate);
                } else {
                    System.out.println(ANSI_YELLOW + "Erro ao obter a taxa de câmbio." + ANSI_RESET);
                }

            } else if (option != 7) {
                System.out.println (ANSI_YELLOW + "Escolha uma opção válida" + ANSI_RESET);
            }
        } while (option != 7);

        System.out.println (ANSI_BLUE + "Aplicação encerrada!" + ANSI_RESET);
        scanner.close();
    }
}
