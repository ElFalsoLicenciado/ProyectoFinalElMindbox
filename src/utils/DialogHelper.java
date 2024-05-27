package utils;

import javax.swing.*;
import java.util.InputMismatchException;

public class DialogHelper {

    public static int optionD(String dialog, String[] options){
        int o;
        do {
            o = JOptionPane.showOptionDialog(null, String.format("%s", dialog), "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        }while (o<0);
        return  o;
    }

    public static int optionD(String dialog,String data, String[] options){
        int o;
        do {
            o = JOptionPane.showOptionDialog(null,String.format("%s%n%s",dialog,data),"",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[0]);
        } while (o<0);
        return o;
    }

    public static String stringIn(String dialog) {
        String input = null;
        do {
            try {
                input = JOptionPane.showInputDialog(null, String.format("%s", dialog), "", JOptionPane.QUESTION_MESSAGE);
            }catch (NullPointerException e){
                DialogHelper.warning("Empty string");
            }
        }while (input==null||input.isEmpty());
        return input;
    }

    public static int intIn(String dialog,int condition1) {
        int input = 0;
        do {
            try {
                input = Integer.parseInt(JOptionPane.showInputDialog(null, String.format("%s", dialog), "", JOptionPane.QUESTION_MESSAGE));
            }catch (NumberFormatException ignored){
            }catch (InputMismatchException e) {
                mismatchD();
            }
        }while (input<=condition1);
        return input;
    }

    public static int int3In(String dialog,String data,int condition1,int condition2) {
        int input = 0;
        do {
            try {
                input = Integer.parseInt(JOptionPane.showInputDialog(null, String.format("%s%n%s", dialog, data), "", JOptionPane.QUESTION_MESSAGE));
            }catch (NumberFormatException ignored){
            }catch (InputMismatchException e) {
                mismatchD();
            }
            if(input<condition1||input>condition2) {outOfBonds();}
        }while (input<condition1||input>condition2);
        return input;
    }

    public static int int3In(String dialog,int condition1,int condition2) {
        int input = 0;
        do {
            try {
                input = Integer.parseInt(JOptionPane.showInputDialog(null, dialog, "", JOptionPane.QUESTION_MESSAGE));
            }catch (NumberFormatException ignored){
            }catch (InputMismatchException e) {
                mismatchD();
            }
            if(input<condition1||input>condition2) {outOfBonds();}
        }while (input<condition1||input>condition2);
        return input;
    }


    public static boolean confirmD(){
        return (JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == 0);
    }

    public static boolean confirmD(String dialog){
        return (JOptionPane.showConfirmDialog(null, dialog, "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == 0);
    }

    public static int confirm2D(String dialog,String data){
        return (JOptionPane.showConfirmDialog(null,String.format("%s%s",dialog,data),"",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null));
    }

    public static void info(String dialog){
        JOptionPane.showMessageDialog(null,dialog,"",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void info(String dialog, String data){
        JOptionPane.showMessageDialog(null,String.format("%s%n%s",dialog,data),"",JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warning(String dialog){
        JOptionPane.showMessageDialog(null,dialog,"",JOptionPane.WARNING_MESSAGE);
    }

    public static void error(String dialog){
        JOptionPane.showMessageDialog(null,dialog,"",JOptionPane.ERROR_MESSAGE);
    }

    public static void mismatchD(){
        JOptionPane.showMessageDialog(null,"Input mismatch.","",JOptionPane.WARNING_MESSAGE);
    }

    public static void returnD(){
        JOptionPane.showMessageDialog(null,"Returning.","",JOptionPane.WARNING_MESSAGE);
    }

    public static void outOfBonds(){ JOptionPane.showMessageDialog(null,"Out of bonds","",JOptionPane.ERROR_MESSAGE);}

}