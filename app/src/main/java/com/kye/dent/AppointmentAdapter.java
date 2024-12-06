package com.kye.dent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private List<AppointmentDTO> appointmentList;
    private final OnAppointmentClickListener listener;

    public interface OnAppointmentClickListener {
        void onAppointmentClick(AppointmentDTO appointment);
    }

    public AppointmentAdapter(List<AppointmentDTO> appointmentList, OnAppointmentClickListener listener) {
        this.appointmentList = appointmentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        AppointmentDTO appointment = appointmentList.get(position);
        holder.bind(appointment, listener);
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    // 새 데이터를 받아와 리스트를 갱신하는 메서드
    public void updateAppointments(List<AppointmentDTO> newAppointments) {
        this.appointmentList = newAppointments;
        notifyDataSetChanged();  // RecyclerView에 데이터 변경을 알림
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView appointmentSummary;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            appointmentSummary = itemView.findViewById(R.id.appointment_summary);
        }

        public void bind(AppointmentDTO appointment, OnAppointmentClickListener listener) {
            String summary = appointment.getAppointmentDate() + " / " +
                    appointment.getTreatmentType();
            appointmentSummary.setText(summary);

            itemView.setOnClickListener(v -> listener.onAppointmentClick(appointment));
        }
    }
}
