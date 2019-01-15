package sicill.utsmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import sicill.utsmobile.R;

import java.util.ArrayList;

public class SupplierAdapter extends BaseAdapter {
    Context context;
    ArrayList<String[]> supplier_list;
    LayoutInflater inflter;

    public SupplierAdapter(Context applicationContext, ArrayList<String[]> supplier_list){
        this.context = applicationContext;
        this.supplier_list = supplier_list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return supplier_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if(!supplier_list.isEmpty()){
            view = inflter.inflate(R.layout.mastersupplierlistview, null);
            TextView txt_kode = (TextView)view.findViewById(R.id.lblkode);
            TextView txt_nama = (TextView)view.findViewById(R.id.lblnama);
            TextView txt_telp= (TextView)view.findViewById(R.id.lbltelp);
            TextView txt_alamat= (TextView)view.findViewById(R.id.lblalamat);
            txt_kode.setText(supplier_list.get(i)[0]);
            txt_nama.setText(supplier_list.get(i)[1]);
            txt_telp.setText(supplier_list.get(i)[2]);
            txt_alamat.setText(supplier_list.get(i)[3]);
        }
        return view;
    }
}
