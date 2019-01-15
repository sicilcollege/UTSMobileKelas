package sicill.utsmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import sicill.utsmobile.R;

import java.util.ArrayList;

public class KaryawanAdapter extends BaseAdapter {
    Context context;
    ArrayList<String[]> karyawan_list;
    LayoutInflater inflter;

    public KaryawanAdapter(Context applicationContext, ArrayList<String[]> karyawan_list){
        this.context = applicationContext;
        this.karyawan_list = karyawan_list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return karyawan_list.size();
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
        if(!karyawan_list.isEmpty()){
            view = inflter.inflate(R.layout.masterkaryawanlistview, null);
            TextView txt_kode = (TextView)view.findViewById(R.id.lblkode);
            TextView txt_nama = (TextView)view.findViewById(R.id.lblnama);
            TextView txt_telp= (TextView)view.findViewById(R.id.lbltelp);
            TextView txt_alamat= (TextView)view.findViewById(R.id.lblalamat);
            TextView txt_password= (TextView)view.findViewById(R.id.lblpassword);
            txt_kode.setText(karyawan_list.get(i)[0]);
            txt_nama.setText(karyawan_list.get(i)[1]);
            txt_telp.setText(karyawan_list.get(i)[2]);
            txt_alamat.setText(karyawan_list.get(i)[3]);
            txt_password.setText(karyawan_list.get(i)[4]);
        }
        return view;
    }
}
