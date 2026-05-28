package com.sg.classroster.ui;

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
        int usrInt;
        while (true) {
            print(prompt);
            try {
                String usrPrompt = sc.nextLine();
                usrInt = Integer.parseInt(usrPrompt);
                break;
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
        return usrInt;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int usrInt;
        while (true) {
            print(prompt);
            try{
                String usrPrompt = sc.nextLine();
                usrInt = Integer.parseInt(usrPrompt);
                if (usrInt < min || usrInt > max) {
                    print("You must enter a number between " + min + " and " + max + ".  Please try again");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
        return usrInt;
    }

    @Override
    public long readLong(String prompt) {
        long usrLong;
        while (true) {
            print(prompt);
            try {
                String usrPrompt = sc.nextLine();
                usrLong = Long.parseLong(usrPrompt);
                break;
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
        return usrLong;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long usrLong;
        while (true) {
            print(prompt);
            try{
                String usrPrompt = sc.nextLine();
                usrLong = Long.parseLong(usrPrompt);
                if (usrLong < min || usrLong > max) {
                    print("You must enter a number between " + min + " and " + max + ".  Please try again");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
        return usrLong;
    }



    @Override
    public float readFloat(String prompt) {
        float usrFloat;
        while (true) {
            print(prompt);
            try {
                String usrPrompt = sc.nextLine();
                usrFloat = Float.parseFloat(usrPrompt);
                break;
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
        return usrFloat;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float usrFloat;
        while (true) {
            print(prompt);
            try{
                String usrPrompt = sc.nextLine();
                usrFloat = Float.parseFloat(usrPrompt);
                if (usrFloat < min || usrFloat > max) {
                    print("You must enter a number between " + min + " and " + max + ".  Please try again");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
        return usrFloat;
    }

    @Override
    public double readDouble(String prompt) {
        double usrDouble;
        while (true) {
            print(prompt);
            try {
                String usrPrompt = sc.nextLine();
                usrDouble = Double.parseDouble(usrPrompt);
                break;
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
        return usrDouble;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double usrDouble;
        while (true) {
            print(prompt);
            try{
                String usrPrompt = sc.nextLine();
                usrDouble = Double.parseDouble(usrPrompt);
                if (usrDouble < min || usrDouble > max) {
                    print("You must enter a number between " + min + " and " + max + ".  Please try again");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                print("Invalid input.  Please try again.");
            }
        }
        return usrDouble;
    }
}
