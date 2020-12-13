package br.com.renanalencar.moreaqui;

import java.io.Serializable;

/***
 * @author renan
 * Classe que implementa a interface command
 * e é responsável por enviar objetos serializados para
 * o servidor
 */
public class SendData implements Command {
    // inteiro longo que representa o id do objeto
    private Long key;
    // objeto serializável que será enviado
    private Serializable value;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    /**
     * Método recebe um objeto do tipo DaoImpl
     * e executa a sua ação.
     * @param d the subject of the actions that will be executed.
     */
    @Override
    public void execute(DaoImpl d) {
        d.add(key, value);
    }
}
