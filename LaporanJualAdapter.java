package sicill.utsmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import sicill.utsmobile.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LaporanJualAdapter extends BaseAdapter {
    Context context;
    ArrayList<String[]> laporan_list;
    LayoutInflater inflter;

    public LaporanJualAdapter(Context applicationContext, ArrayList<String[]> ls){
        this.context = applicationContext;
        this.laporan_list = ls;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return laporan_list.size();
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
        if(!laporan_list.isEmpty()){
            view = inflter.inflate(R.layout.laporanpembelianlistview, null);
            TextView txt_id = (TextView)view.findViewById(R.id.txt_id_lap);
            TextView txt_desc = (TextView)view.findViewById(R.id.txt_desc_lap);
            TextView txt_desc2 = (TextView)view.findViewById(R.id.txt_desc_lap2);
            TextView txt_tot_lap = (TextView)view.findViewById(R.id.txt_tot_lap);

            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

            txt_id.setText(laporan_list.get(i)[0]);

            txt_desc.setText("Tanggal : "+laporan_list.get(i)[1]);
            txt_desc2.setText("Customer : "+laporan_list.get(i)[2]);

            Double hrbeli = Double.parseDouble(laporan_list.get(i)[3]);
            String hbeli = formatRupiah.format((double)hrbeli);

            txt_tot_lap.setText(hbeli);

        }
        return view;
    }
}

