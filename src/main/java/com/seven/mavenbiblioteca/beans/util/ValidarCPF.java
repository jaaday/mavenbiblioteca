package com.seven.mavenbiblioteca.beans.util;

public class ValidarCPF {

    private String cpf;

    private boolean verificarSeTamanhoInvalido(String cpf) {
        return cpf.length() != 11;
    }

    private boolean verificarSeDigIguais(String cpf) {
        //char primDig = cpf.charAt(0);
        char primDig = '0';
        char[] charCpf = cpf.toCharArray();
        for (char c : charCpf) {
            if (c != primDig) {
                return false;
            }
        }
        return true;
    }

    private String calculoComCpf(String cpf) {
        int digGerado = 0;
        int mult = cpf.length() + 1;
        char[] charCpf = cpf.toCharArray();
        for (int i = 0; i < cpf.length(); i++) {
            digGerado += (charCpf[i] - 48) * mult--;
        }
        if (digGerado % 11 < 2) {
            digGerado = 0;
        } else {
            digGerado = 11 - digGerado % 11;
        }
        return String.valueOf(digGerado);
    }

    public boolean validarCpf(String cpf) {

        if (cpf == null) {
            return false;
        } else {
            String cpfGerado = "";
            this.cpf = cpf;

            if (verificarSeTamanhoInvalido(this.cpf)) {
                return false;
            }

            if (verificarSeDigIguais(this.cpf)) {
                return false;
            }

            cpfGerado = this.cpf.substring(0, 9);
            cpfGerado = cpfGerado.concat(calculoComCpf(cpfGerado));
            cpfGerado = cpfGerado.concat(calculoComCpf(cpfGerado));

            if (!cpfGerado.equals(this.cpf)) {
                return false;
            }
        }
        return true;
    }

}
