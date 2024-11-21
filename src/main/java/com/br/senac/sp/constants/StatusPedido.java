package com.br.senac.sp.constants;

public enum StatusPedido {
    AGUARDANDO_PAGAMENTO("Aguardando pagamento"),
    PAGAMENTO_REJEITADO("Pagamento rejeitado"),
    PAGAMENTO_COM_SUCESSO("Pagamento com sucesso"),
    AGUARDANDO_RETIRADA("Aguardando retirada"),
    EM_TRANSITO("Em transito"),
    ENTREGUE("Entregue");

    private final String displayName;

    StatusPedido(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static boolean exists(String status) {
        for (StatusPedido s : StatusPedido.values()) {
            if (s.getDisplayName().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}