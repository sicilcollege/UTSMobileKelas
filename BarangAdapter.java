package sicill.utsmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import sicill.utsmobile.R;

import java.util.ArrayList;

public class BarangAdapter extends BaseAdapter {
    Context context;
    ArrayList<String[]> barang_list;
    LayoutInflater inflter;

    public BarangAdapter(Context applicationContext, ArrayList<String[]> barang_list){
        this.context = applicationContext;
        this.barang_list = barang_list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return barang_list.size();
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
        if(!barang_list.isEmpty()){
            view = inflter.inflate(R.layout.masterbaranglistview, null);
            TextView txt_kode = (TextView)view.findViewById(R.id.lblkode);
            TextView txt_nama = (TextView)view.findViewById(R.id.lblnama);
            TextView txt_satuan= (TextView)view.findViewById(R.id.lblsatuan);
            TextView txt_stok= (TextView)view.findViewById(R.id.lblstok);
            TextView txt_beli= (TextView)view.findViewById(R.id.lblbeli);
            TextView txt_jual= (TextView)view.findViewById(R.id.lbljual);
            txt_kode.setText(barang_list.get(i)[0]);
            txt_nama.setText(barang_list.get(i)[1]);
            txt_satuan.setText(barang_list.get(i)[2]);
            txt_stok.setText(barang_list.get(i)[3]);
            txt_beli.setText(barang_list.get(i)[4]);
            txt_jual.setText(barang_list.get(i)[5]);
        }
        return view;
    }
}
