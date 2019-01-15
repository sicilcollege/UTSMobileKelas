package sicill.utsmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import sicill.utsmobile.R;

public class AdapterStok extends BaseAdapter {
    Context context;
    String listnamabarang[],liststokawal[],liststokbeli[],liststokjual[],liststokakhir[];
    LayoutInflater inflter;

    public AdapterStok (Context applicationContext, String[] listnamabarang, String[] liststokawal,String[] liststokbeli, String[] liststokjual, String[] liststokakhir){
        this.context = context;
        this.listnamabarang = listnamabarang;
        this.liststokawal = liststokawal;
        this.liststokbeli = liststokbeli;
        this.liststokjual = liststokjual;
        this.liststokakhir = liststokakhir;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount(){
        return listnamabarang.length;
    }
    @Override
    public Object getItem(int position){
        return null;
    }
    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        view = inflter.inflate(R.layout.laporanstok,null);
        TextView namabarang = (TextView) view.findViewById(R.id.txtNamabarang);
        TextView stokawal = (TextView) view.findViewById(R.id.txtStokawal);
        TextView stokbeli = (TextView) view.findViewById(R.id.txtStokpembelian);
        TextView stokjual = (TextView) view.findViewById(R.id.txtStokpenjualan);
        TextView stokakhir = (TextView) view.findViewById(R.id.txtStokakhir);

        namabarang.setText(listnamabarang[position]);
        stokawal.setText(liststokawal[position]);
        stokbeli.setText(liststokbeli[position]);
        stokjual.setText(liststokjual[position]);
        stokakhir.setText(liststokakhir[position]);
        return view;
    }
}
