/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.laboratorio1;

import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author Rosse Lorenzana
 */


public class Laboratorio1{
    
    // metodo para verificar si un caracter es un operador
    public static boolean isOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // metodo para invertir una cadena y cambiar '(' por ')' y viceversa
    public static String reverseString(String str) {
        StringBuilder reversed = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (c == '(') {
                reversed.append(')');
            } else if (c == ')') {
                reversed.append('(');
            } else {
                reversed.append(c);
            }
        }
        return reversed.toString();
    }

    // metodo para validar la jerarquia de los operadores
    public static int validateHierarchy(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    // metodo para convertir una expresión infija a postfija
    public static String convertToPostfix(String expresionInfija) {
        StringBuilder resultado = new StringBuilder();
        Stack<Character> pila = new Stack<>();

        for (int i = 0; i < expresionInfija.length(); i++) {
            char c = expresionInfija.charAt(i);

            // si el caracter es un operando, añadirlo al resultado
            if (Character.isLetterOrDigit(c)) {
                resultado.append(c);
            }
            // si el caracter es '(', empujarlo a la pila
            else if (c == '(') {
                pila.push(c);
            }
            // si el caracter es ')', sacar de la pila hasta encontrar '('
            else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    resultado.append(pila.pop());
                }
                if (!pila.isEmpty() && pila.peek() == '(') {
                    pila.pop();                 // sacar '(' de la pila
                }
            }
            // si el caracter es un operador
            else if (isOperador(c)) {
                while (!pila.isEmpty() && validateHierarchy(pila.peek()) >= validateHierarchy(c)) {
                    resultado.append(pila.pop());
                }
                pila.push(c);
            }
        }

        // sacar todos los operadores restantes de la pila
        while (!pila.isEmpty()) {
            resultado.append(pila.pop());
        }

        return resultado.toString();
    }

    // metodo para convertir una expresion infija a prefija
    public static String convertToPrefix(String expresionInfija) {
        // invertir la expresión infija y cambiar '(' por ')' y viceversa
        String expresionInvertida = reverseString(expresionInfija);

        // convertir la expresion invertida a postfija
        String expresionPostfijaInvertida = convertToPostfix(expresionInvertida);

        // invertir la expresion postfija para obtener la prefija
        return reverseString(expresionPostfijaInvertida);
    }

    // metodo principal
    public static void main(String[] args) {
        // mostrar una caja de texto para que el usuario ingrese la expresion infija
        String expresionInfija = JOptionPane.showInputDialog("Ingrese la expresión infija:");

        if (expresionInfija != null && !expresionInfija.isEmpty()) {
            // Convertir la expresion infija a postfija y prefija
            String expresionPostfija = convertToPostfix(expresionInfija);
            String expresionPrefija = convertToPrefix(expresionInfija);

            // mostrar los resultados en un cuadro de dialogo
            String mensaje = "Expresión Infija: " + expresionInfija + "\n"
                           + "Expresión Postfija: " + expresionPostfija + "\n"
                           + "Expresión Prefija: " + expresionPrefija;

            JOptionPane.showMessageDialog(null, mensaje, "Resultados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se ingresó ninguna expresión.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
