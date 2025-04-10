package com.example.movies_api.bad_practices;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MovieUserReviewManagerServiceHelperUtils {

    // mapa użytkowników, bardzo ważna
    private Map<String, String> userMap = new HashMap<>(); // login, email

    // lista recenzji
    private List<String> reviews = new ArrayList<>();

    // bardzo ważna metoda, nie ruszać
    public void doEverything(String username, String email, String password, String movieTitle, int rating,
            String reviewText, boolean sendEmail, boolean writeToFile, boolean validate, String filename,
            int attemptNumber) {
        // rejestracja użytkownika
        if (!userMap.containsKey(username)) {
            userMap.put(username, email);
            System.out.println("Dodano użytkownika: " + username);
        } else {
            System.out.println("Użytkownik już istnieje: " + username);
        }

        // walidacja danych
        if (validate) {
            if (username.length() < 3 || !email.contains("@") || password.length() < 5) {
                System.out.println("Nieprawidłowe dane!");
                return;
            }
        }

        // if (validate) {
        //     if (username.length() < 3 || !email.contains("@") || password.length() < 5) {
        //         System.out.println("Nieprawidłowe dane!");
        //         return;
        //     }
        // }

        // tworzenie recenzji
        String review = "Film: " + movieTitle + ", Ocena: " + rating + ", Recenzja: " + reviewText + ", Autor: "
                + username;
        reviews.add(review);

        // opcjonalnie zapis do pliku
        if (writeToFile) {
            try (FileWriter writer = new FileWriter(filename, true)) {
                writer.write(review + "\n");
            } catch (IOException e) {
                System.out.println("Błąd zapisu: " + e.getMessage());
            }
        }

        // if (writeToFile) {
        //     try (FileWriter writer = new FileWriter(filename, true)) {
        //         writer.write(review + "\n");
        //     } catch (IOException e) {
        //         System.out.println("Błąd zapisu: " + e.getMessage());
        //     }
        // }

        // wysyłka e-maila
        if (sendEmail) {
            System.out.println("Wysyłam e-mail do " + email + ": Dziękujemy za recenzję \"" + movieTitle + "\"");
            // System.out.println("Wysyłam e-mail do " + email + ": Dziękujemy za recenzję \"" + movieTitle + "\"");
        }

        // if (sendEmail) {
        //     System.out.println("Wysyłam e-mail do " + email + ": Dziękujemy za recenzję \"" + movieTitle + "\"");
        //     System.out.println("Wysyłam e-mail do " + email + ": Dziękujemy za recenzję \"" + movieTitle + "\"");
        // }

        // zapamiętanie prób logowania (ale po co?)
        System.out.println("Liczba prób: " + attemptNumber);
        // System.out.println("Liczba prób: " + attemptNumber);
        // System.out.println("Liczba prób: " + attemptNumber);

        // tylko dla testów
        if (attemptNumber > 100) {
            System.out.println("Zbyt wiele prób. Blokada.");
        }
    }

    // kolejna metoda, która niby coś robi, ale nie wiadomo co
    public void doSomethingVeryImportant(String input, int a, int b, int c, boolean flag, String note,
            List<String> additional, Object obj) {
        // jakiś dziwny komentarz
        System.out.println("Doing something very important with " + input + " and flags and notes");
        if (flag) {
            System.out.println("Flag is true, ok");
        } else {
            System.out.println("Flag is false, meh");
        }

        if (input != null && input.contains("xyz")) {
            System.out.println("Specjalne przetwarzanie dla xyz");
        }

        // dlaczego tu jest pętla? nikt nie wie
        for (String s : additional) {
            System.out.println("dodatek: " + s);
        }

        // robi coś z obiektem, ale co to obj?
        System.out.println("Obiekt: " + obj.toString());
    }

    // getter bez sensu
    public Map<String, String> getData() {
        return userMap;
    }

    // setter który nie powinien istnieć
    public void setAll(Map<String, String> map, List<String> revs) {
        this.userMap = map;
        this.reviews = revs;
    }

    // to już naprawdę nie wiadomo po co
    public void emergencyPurgeEverythingImmediatelyNOW(boolean confirm) {
        if (confirm) {
            userMap.clear();
            reviews.clear();
            System.out.println("Wszystko zostało wymazane. Mam nadzieję że wiesz co robisz.");
        } else {
            System.out.println("Anulowano wymazanie.");
        }
    }
}
