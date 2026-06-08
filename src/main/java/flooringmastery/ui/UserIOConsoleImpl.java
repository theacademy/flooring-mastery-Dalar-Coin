package flooringmastery.ui;

import flooringmastery.service.FlooringMasteryDataValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }

    @Override
    public int readInt(String prompt) {
        while (true) {
            print(prompt);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        while (true) {
            print(prompt);
            try {
                int usrInt = Integer.parseInt(sc.nextLine());
                if (usrInt < min || usrInt > max) {
                    print("You must enter a number between " + min + " and " + max + ".  Please try again");
                } else {
                    return usrInt;
                }
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
    }

    @Override
    public long readLong(String prompt) {
        while (true) {
            print(prompt);
            try {
                return Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        while (true) {
            print(prompt);
            try {
                long usrLong = Long.parseLong(sc.nextLine());
                if (usrLong < min || usrLong > max) {
                    print("You must enter a number between " + min + " and " + max + ".  Please try again");
                } else {
                    return usrLong;
                }
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
    }

    @Override
    public float readFloat(String prompt) {
        while (true) {
            print(prompt);
            try {
                return Float.parseFloat(sc.nextLine());
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        while (true) {
            print(prompt);
            try {
                float usrFloat = Float.parseFloat(sc.nextLine());
                if (usrFloat < min || usrFloat > max) {
                    print("You must enter a number between " + min + " and " + max + ".  Please try again");
                } else {
                    return usrFloat;
                }
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
    }

    @Override
    public double readDouble(String prompt) {
        while (true) {
            print(prompt);
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        while (true) {
            print(prompt);
            try {
                double usrDouble = Double.parseDouble(sc.nextLine());
                if (usrDouble < min || usrDouble > max) {
                    print("You must enter a number between " + min + " and " + max + ".  Please try again");
                } else {
                    return usrDouble;
                }
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
    }

    @Override
    public LocalDate readLocalDate(String prompt) throws FlooringMasteryDataValidationException {
        print(prompt);
        try {
            return LocalDate.parse(sc.nextLine(),
                    DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeParseException e) {
            throw new FlooringMasteryDataValidationException(
                    "Date format was incorrect.  Please input in the form MM/dd/yyyy");
        }
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) throws FlooringMasteryDataValidationException {
        print(prompt);
        try {
            return new BigDecimal(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new FlooringMasteryDataValidationException(
                    "Invalid input. Please enter a number.");
        }
    }
}