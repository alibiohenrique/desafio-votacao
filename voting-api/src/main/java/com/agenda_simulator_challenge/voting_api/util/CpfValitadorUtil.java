package com.agenda_simulator_challenge.voting_api.util;

public class CpfValitadorUtil {

        // Constante usada como índice inicial para os cálculos dos dígitos verificadores
        private static final int POINTER = 2;
        /**
         * Valida um CPF verificando se ele possui 11 dígitos e se os dígitos verificadores são corretos.
         *
         * @param cpf O CPF a ser validado.
         * @return true se o CPF for válido, false caso contrário.
         * @throws IllegalArgumentException Caso o CPF não tenha 11 dígitos numéricos.
         */
        public static boolean isCpfValid(String cpf) {

            // Retira o que não for número
            cpf = cpf.replaceAll("[^0-9]", "");

            if (cpf.length() != 11) {
                throw new IllegalArgumentException("Digite um CPF com 11 dígitos");
            }

            // Verifica se o CPF é uma sequência de números repetidos (ex: "11111111111")
            boolean allDigitsArenTheSame = true;
            char firstChar = cpf.charAt(0);
            for (int i = 1; i < cpf.length(); i++) {
                if (cpf.charAt(i) != firstChar) {
                    allDigitsArenTheSame = false;
                    break;
                }
            }
            if (allDigitsArenTheSame) {
                return false;
            }

            // Cálculo do primeiro dígito verificador
            int sum = firstNumber(cpf);
            int module = sum % 11;
            int firstDigit = (module >= 2) ? (11 - module) : 0;

            // Cálculo do segundo dígito verificador
            int secondNumberSum = secondNumber(cpf, firstDigit);
            int secondNumberModule = secondNumberSum % 11;
            int secondDigit = (secondNumberModule >= 2) ? (11 - secondNumberModule) : 0;

            return cpf.charAt(9) == Character.forDigit(firstDigit, 10) && cpf.charAt(10) == Character.forDigit(secondDigit, 10);
        }

        private static int firstNumber(String cpf) {
            int pointer = POINTER;
            int sum = 0;
            for (int i = cpf.length() - 3; i >= 0; i--) {
                int multiplyNumber = Character.getNumericValue(cpf.charAt(i)) * pointer;
                pointer++;
                sum += multiplyNumber;
            }
            return sum;
        }

        private static int secondNumber(String cpf, int firstDigit) {
            boolean firstInteraction = true;
            int multiplyNumber;
            int pointer = POINTER;
            int sum = 0;

            for (int i = cpf.length() - 2; i >= 0; i--) {
                if (firstInteraction) {
                    firstInteraction = false;
                    multiplyNumber = pointer * firstDigit;
                    sum += multiplyNumber;
                    pointer++;
                } else {
                    multiplyNumber = Character.getNumericValue(cpf.charAt(i)) * pointer;
                    sum += multiplyNumber;
                    pointer++;
                }
            }
            return sum;
        }

// Referência para o cálculo
// https://www.macoratti.net/alg_cpf.htm
}
