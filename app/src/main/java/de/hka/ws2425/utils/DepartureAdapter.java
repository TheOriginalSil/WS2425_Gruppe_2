package de.hka.ws2425.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hka.ws2425.R;
import io.reactivex.annotations.NonNull;

public class DepartureAdapter extends RecyclerView.Adapter<DepartureAdapter.DepartureViewHolder> {

    private List<Departure> departures;
    private final SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");

    public DepartureAdapter(List<Departure> departures) {
        this.departures = departures;
    }

    @NonNull
    @Override
    public DepartureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_departure, parent, false);
        return new DepartureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartureViewHolder holder, int position) {
        Departure departure = departures.get(position);

        // Abfahrtszeit formatieren und grün anzeigen
        holder.departureTime.setText(formatTime(departure.getDepartureTime()) + " Uhr");

        // Linie prominent anzeigen
        holder.routeShortName.setText("Linie " + departure.getRouteShortName());

        // Zielort setzen
        holder.tripHeadsign.setText(departure.getTripHeadsign());
    }

    @Override
    public int getItemCount() {
        return departures.size();
    }

    private String formatTime(String time) {
        try {
            Date date = inputFormat.parse(time);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return time; // Falls Fehler, bleibt die Originalzeit
        }
    }

    static class DepartureViewHolder extends RecyclerView.ViewHolder {
        TextView routeShortName, tripHeadsign, departureTime;

        public DepartureViewHolder(@NonNull View itemView) {
            super(itemView);
            routeShortName = itemView.findViewById(R.id.routeShortName);
            tripHeadsign = itemView.findViewById(R.id.tripHeadsign);
            departureTime = itemView.findViewById(R.id.departureTime);
        }
    }
}