package com.br.senac.sp.constants;

public enum Pagamento {
    CARTAO_CREDITO,
    PIX;

    public static boolean isValid(String value) {
        for (Pagamento pagamento : Pagamento.values()) {
            if (pagamento.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
