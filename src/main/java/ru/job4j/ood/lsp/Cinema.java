package ru.job4j.ood.lsp;

public class Cinema {
    public void filmLang(Object obj) {
        if (obj instanceof RusCinema) {
            System.out.println("Film with rus language");
        } else {
            System.out.println("Info is absent");
        }
    }

    public void permitAccess(int age) {
        if (age >= 18) {
            System.out.println("Access granted");
        } else {
            System.out.println("Access denied");
        }
    }

    public static void main(String[] args) {
        Cinema cinema = new RusCinema();
        cinema.permitAccess(16);
        cinema.filmLang(cinema);
        cinema = new USCinema();
        cinema.permitAccess(19);
        cinema.filmLang(cinema);
    }
}

class RusCinema extends Cinema {
    @Override
    public void permitAccess(int age) {
        if (age >= 16) {
            System.out.println("Access granted");
        } else {
            System.out.println("Access denied");
        }
    }
}

class USCinema extends Cinema {
    @Override
    public void permitAccess(int age) {
        if (age >= 21) {
            System.out.println("Access granted");
        } else {
            System.out.println("Access denied");
        }
    }
}
