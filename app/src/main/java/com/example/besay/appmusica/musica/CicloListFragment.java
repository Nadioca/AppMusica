package com.example.besay.appmusica.musica;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.besay.appmusica.R;
import com.example.besay.appmusica.constantes.Constantes;
import com.example.besay.appmusica.constantes.Utilidades;
import com.example.besay.appmusica.pojos.Categorias;
import com.example.besay.appmusica.pojos.Musica;
import com.example.besay.appmusica.proveedor.CategoriasProveedor;
import com.example.besay.appmusica.proveedor.Contrato;
import com.example.besay.appmusica.proveedor.MusicaProveedor;

import java.io.FileNotFoundException;


public class CicloListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    CicloCursorAdapter mAdapter;
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    ActionMode mActionMode;
    View viewSeleccionado;

    public static CicloListFragment newInstance() {
        CicloListFragment f = new CicloListFragment();

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem = menu.add(Menu.NONE, Constantes.INSERTAR, Menu.NONE, "INSERTAR");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.ic_add);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case Constantes.INSERTAR:
                Intent intent = new Intent(getActivity(), CicloInsercionActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.i(LOGTAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_ciclo_list, container, false);

        mAdapter = new CicloCursorAdapter(getActivity());
        setListAdapter(mAdapter);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i(LOGTAG, "onActivityCreated");

        mCallbacks = this;

        getLoaderManager().initLoader(0, null, mCallbacks);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(mActionMode!=null){
                    return false;
                }
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                view.setSelected(true);
                viewSeleccionado = view;
                return true;
            }
        });
    }

    ActionMode.Callback mActionModeCallback = new ActionMode.Callback(){

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
                case R.id.menu_contextual_borrar:
                    int cicloId = (Integer) viewSeleccionado.getTag();
                    MusicaProveedor.delete(getActivity().getContentResolver(), cicloId);
                    break;
                case R.id.menu_contextual_editar:
                    Intent intent = new Intent(getActivity(), CicloActualizacionActivity.class);
                    cicloId = (Integer) viewSeleccionado.getTag();
                    Log.i("El identificador 1", "kk"+cicloId);
                    Musica ciclo = MusicaProveedor.read(getActivity().getContentResolver(), cicloId);
                    Log.i("El identificador", ciclo.getCompa());
                    intent.putExtra("ID", ciclo.getId_musica());
                    intent.putExtra("Nombre", ciclo.getTitulo());
                    intent.putExtra("Abreviatura", ciclo.getCompa());
                    intent.putExtra("Id_Categoria", ciclo.getCategoria().getID());
                    startActivity(intent);
                    break;
                default:
                    return false;
            }
            mode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        String columns[] = new String[] { Contrato.Musica._ID,
                                          Contrato.Musica.NOMBRE,
                                          Contrato.Musica.COMPA,
										  Contrato.Musica.ID_CATEGORIA
                                        };

        Uri baseUri = Contrato.Musica.CONTENT_URI;

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

        String selection = null;

        return new CursorLoader(getActivity(), baseUri,
                columns, selection, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)

        Uri laUriBase = Uri.parse("content://"+Contrato.AUTHORITY+"/Musica");
        data.setNotificationUri(getActivity().getContentResolver(), laUriBase);

        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    public class CicloCursorAdapter extends CursorAdapter {
        public CicloCursorAdapter(Context context) {
            super(context, null, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int ID = cursor.getInt(cursor.getColumnIndex(Contrato.Musica._ID));
            String nombre = cursor.getString(cursor.getColumnIndex(Contrato.Musica.NOMBRE));
            String abreviatura = cursor.getString(cursor.getColumnIndex(Contrato.Musica.COMPA));
            int idCategoria = cursor.getInt(cursor.getColumnIndex(Contrato.Musica.ID_CATEGORIA));

			Categorias categoria = CategoriasProveedor.read(getContext().getContentResolver(), idCategoria);

            TextView textviewNombre = (TextView) view.findViewById(R.id.textview_ciclo_list_item_nombre);
            textviewNombre.setText("Autor: "+nombre);

            TextView textviewAbreviatura = (TextView) view.findViewById(R.id.textview_ciclo_list_item_abreviatura);
            textviewAbreviatura.setText("CD: "+abreviatura);

            TextView textViewCategoria = (TextView) view.findViewById(R.id.textview_ciclo_list_item_categoria);
            textViewCategoria.setText("categoria: "+categoria.getTitulo());

            ImageView image = (ImageView) view.findViewById(R.id.image_view);

            try {
                Utilidades.loadImageFromStorage(getActivity(), "img_" + ID + ".jpg", image);
            } catch (FileNotFoundException e) {
                ponerImagenDeLetra(abreviatura, image);
            }

            view.setTag(ID);

        }

        private void ponerImagenDeLetra(String abreviatura, ImageView image) {
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            int color = generator.getColor(abreviatura); //Genera un color según el nombre
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(abreviatura.substring(0, 1), color);
            image.setImageDrawable(drawable);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.ciclo_list_item, parent, false);
            bindView(v, context, cursor);
            return v;
        }
    }
}
