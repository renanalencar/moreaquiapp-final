package br.com.renanalencar.moreaqui;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/***
 * @author renan
 */
class RecordData extends AsyncTask<List<LocationEstate>, Void, Void> {

    private Exception exception;

    /**
     *
     * @param locationEstates
     * @return
     */
    @Override
    protected Void doInBackground(List<LocationEstate>... locationEstates) {
        DaoImpl dao = new DaoImpl();
        SendData sendData = new SendData();
        Invoker invoker = new Invoker(DAO.HOST, DAO.PORT);
        // enquanto houver objeto dentro da lista faça:
        for (LocationEstate estate: locationEstates[0]
             ) {
//            sendData.setKey(new Long(estate.hashCode()));
            // configura uma id para o objeto a ser enviado
            sendData.setKey(Long.valueOf(estate.hashCode()));
            // configura um objeto serializavel que vai ser enviado
            sendData.setValue(estate);
            // escreve no log se deu certo o envio d objeto
            Log.v("Serializable", String.valueOf(sendData.getKey()));
            // chama o invoker para executar o comando add e enviar
            // o objeto serializado para o servidor
            invoker.invoke(dao, sendData);
        }

        return null;
    }
}