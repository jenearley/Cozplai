package edu.sjsu.jen.cozplai;

import android.app.AlertDialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

/**
 * Created by jen0e on 12/8/17.
 */

public class ConventionAdapter extends RecyclerView.Adapter<ConventionAdapter.ConventionViewHolder>{
    List<Convention> conventions;

    public ConventionAdapter(List<Convention> conventions) {
        this.conventions = conventions;
    }

    @Override
    public ConventionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.convention_layout, parent, false);
       ConventionAdapter.ConventionViewHolder conventionViewHolder = new ConventionAdapter.ConventionViewHolder(v);
        return conventionViewHolder;
    }

    @Override
    public void onBindViewHolder(ConventionAdapter.ConventionViewHolder holder, int position) {
        Convention convention = conventions.get(position);
        holder.conventionName.setText(convention.getName());
        holder.conventionDate.setText(convention.getDate());

        Geocoder geocoder = new Geocoder(holder.conventionDate.getContext());
        try {
            List<Address> addressList = geocoder.getFromLocationName(convention.getAddress(), 1);
            Address address = addressList.get(0);
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            WeatherApi.getWeatherAtLocation(lat, lng, holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return conventions.size();
    }

    public static class ConventionViewHolder extends RecyclerView.ViewHolder implements WeatherCallback {
        TextView conventionName;
        TextView conventionDate;
        ImageButton conventionWeather;
        WeatherApiResult weatherApiResult;

        private AlertDialog currentDialog;

        public ConventionViewHolder(final View itemView) {
            super(itemView);
            conventionName = (TextView) itemView.findViewById(R.id.convention_name_textview);
            conventionDate = (TextView) itemView.findViewById(R.id.convention_date_textview);
            conventionWeather = (ImageButton) itemView.findViewById(R.id.convention_weather_button);

            conventionWeather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Weather Report");

                    LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                    View dialogView = inflater.inflate(R.layout.weather_dialog, null);
                    if (weatherApiResult != null) {
                        TextView cityName = (TextView) dialogView.findViewById(R.id.weather_city_textview);
                        cityName.setText(weatherApiResult.name);

                        TextView cityTemp = (TextView) dialogView.findViewById(R.id.weather_temp_textview);
                        int temp = (int) (1.8 * (weatherApiResult.main.getTemp() - 273) + 32);
                        cityTemp.setText( temp + "°F");

                        TextView cityMin = (TextView) dialogView.findViewById(R.id.weather_min_temp_textview);
                        int minTemp = (int) (1.8 * (weatherApiResult.main.getTemp_min() - 273) + 32);
                        cityMin.setText("Min: " + minTemp + "°F");

                        TextView cityMax = (TextView) dialogView.findViewById(R.id.weather_max_temp_textview);
                        int maxTemp = (int) (1.8 * (weatherApiResult.main.getTemp_max() - 273) + 32);
                        cityMax.setText("Max: " + maxTemp + "°F");

                        TextView cityHumidity = (TextView) dialogView.findViewById(R.id.weather_humidity_textview);
                        cityHumidity.setText("Humidity: " + weatherApiResult.main.getHumidity() + "%");

                        TextView cityPressure = (TextView) dialogView.findViewById(R.id.weather_pressure_textview);
                        cityPressure.setText("Pressure: " + weatherApiResult.main.getPressure() + " hpa");
                    }
                    builder.setView(dialogView);

                    currentDialog = builder.create();
                    currentDialog.show();

                }
            });
        }

        @Override
        public void onWeatherResult(String weather) {
            weatherApiResult = WeatherApiResult.fromString(weather);
        }
    }
}
