package com.example.test.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.ArrayList;

public class ObjectCardPage extends AppCompatActivity {

    private RecyclerView nameRecyclerView;
    private RecyclerView addressRecyclerView;
    private RecyclerView categRecyclerView;
    private RecyclerView gradesRecyclerView;
    private RecyclerView workingHoursRecyclerView;
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> addressList = new ArrayList<>();
    private ArrayList<String> categList = new ArrayList<>();
    private ArrayList<GradeData> gradeList = new ArrayList<>();
    private TextView phoneNumberText;

    private boolean isFavorite = false;

    private RecyclerView imagesRecyclerView; // Removed duplicate declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_card_page);

        // Initialize general views
        nameRecyclerView = findViewById(R.id.name_view);
        addressRecyclerView = findViewById(R.id.adress_view);
        categRecyclerView = findViewById(R.id.categ_view);
        gradesRecyclerView = findViewById(R.id.grades_view);
        workingHoursRecyclerView = findViewById(R.id.working_hours_view);
        phoneNumberText = findViewById(R.id.phone_number_text);
        imagesRecyclerView = findViewById(R.id.images_view);


        // Initialize favorite button
        ImageView favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            //private boolean isFavorite = false;

            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                favoriteButton.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border_card);
                Toast.makeText(v.getContext(), isFavorite ? "Added to favorites" : "Removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });

        // Mock data for other RecyclerViews (replace with your actual data retrieval logic)
        nameList.add(getString(R.string.name_placeholder));
        addressList.add(getString(R.string.address_placeholder));
        categList.add(getString(R.string.categ_placeholder));
        gradeList.add(new GradeData(4.3f, 20));

        // Create and set adapters for other RecyclerViews
        nameRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nameRecyclerView.setAdapter(new NameAdapter(nameList));

        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressRecyclerView.setAdapter(new AddressAdapter(addressList));

        categRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categRecyclerView.setAdapter(new CategAdapter(categList));

        gradesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gradesRecyclerView.setAdapter(new GradesAdapter(gradeList));

        // Initialize and set adapter for working hours RecyclerView
        workingHoursRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        workingHoursRecyclerView.setAdapter(new WorkingHoursAdapter(getWorkingHoursData()));

        // Fetch and display phone number from database (mocked with placeholder)
        String phoneNumber = "+1234567890"; // Replace with actual database retrieval
        phoneNumberText.setText(phoneNumber);

        // Set click listener to copy phone number on click
        phoneNumberText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyPhoneNumberToClipboard(phoneNumber);
            }
        });

        // Initialize and set adapter for images RecyclerView
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesRecyclerView.setAdapter(new ImagesAdapter(this));
    }


    // Method to copy phone number to clipboard
    private void copyPhoneNumberToClipboard(String phoneNumber) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Phone Number", phoneNumber);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Phone number copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    // Method to fetch working hours data (replace with actual database logic)
    private ArrayList<WorkingHoursData> getWorkingHoursData() {
        ArrayList<WorkingHoursData> data = new ArrayList<>();
        data.add(new WorkingHoursData("Ежедневно с 9:00 до 18:00", true)); // Mock data
        return data;
    }

    // Adapter for images RecyclerView
    public static class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

        private Context context;
        private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3}; // Replace with your images

        public ImagesAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            return new ImageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            holder.imageView.setImageResource(images[position]);
            // Отдельный обработчик для сердечка
            if (holder.favoriteButton != null) {
                // Устанавливаем начальное изображение в зависимости от состояния
                holder.favoriteButton.setImageResource(holder.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                // Слушаем нажатие и меняем иконку
                holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.isFavorite = !holder.isFavorite;
                        holder.favoriteButton.setImageResource(holder.isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
                        Toast.makeText(v.getContext(), holder.isFavorite ? "Added to favorites" : "Removed from favorites", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        public static class ImageViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private ImageView favoriteButton; // Добавлено для сердечка
            private boolean isFavorite; // Статус избранного

            public ImageViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                favoriteButton = itemView.findViewById(R.id.favorite_button); // Находим сердечко по ID
            }
        }
    }

    // Adapter for working hours RecyclerView
    public static class WorkingHoursAdapter extends RecyclerView.Adapter<WorkingHoursAdapter.WorkingHoursViewHolder> {

        private ArrayList<WorkingHoursData> workingHoursList;

        public WorkingHoursAdapter(ArrayList<WorkingHoursData> workingHoursList) {
            this.workingHoursList = workingHoursList;
        }

        @NonNull
        @Override
        public WorkingHoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_working_hours, parent, false);
            return new WorkingHoursViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull WorkingHoursViewHolder holder, int position) {
            WorkingHoursData workingHoursData = workingHoursList.get(position);
            holder.bind(workingHoursData);
        }

        @Override
        public int getItemCount() {
            return workingHoursList.size();
        }

        public static class WorkingHoursViewHolder extends RecyclerView.ViewHolder {
            private TextView hoursTextView;
            private TextView statusTextView;

            public WorkingHoursViewHolder(View itemView) {
                super(itemView);
                hoursTextView = itemView.findViewById(R.id.hours_text);
                statusTextView = itemView.findViewById(R.id.status_text);
            }

            public void bind(WorkingHoursData workingHoursData) {
                hoursTextView.setText(workingHoursData.hoursText);
                if (workingHoursData.isOpen) {
                    statusTextView.setText("Открыто до 18:00");
                    statusTextView.setTextColor(itemView.getContext().getResources().getColor(R.color.green)); // Change color dynamically
                } else {
                    statusTextView.setText("Закрыто до 9:00");
                    statusTextView.setTextColor(itemView.getContext().getResources().getColor(R.color.red)); // Change color dynamically
                }
            }
        }
    }

    // Data class for grade data
    public static class GradeData {
        public float rating;
        public int ratingCount;

        public GradeData(float rating, int ratingCount) {
            this.rating = rating;
            this.ratingCount = ratingCount;
        }
    }

    // Adapter for gradesRecyclerView
    public static class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradesViewHolder> {

        private ArrayList<GradeData> gradeList;

        public GradesAdapter(ArrayList<GradeData> gradeList) {
            this.gradeList = gradeList;
        }

        @NonNull
        @Override
        public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grade, parent, false);
            return new GradesViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull GradesViewHolder holder, int position) {
            GradeData gradeData = gradeList.get(position);
            holder.bind(gradeData);
        }

        @Override
        public int getItemCount() {
            return gradeList.size();
        }

        public static class GradesViewHolder extends RecyclerView.ViewHolder {
            private LinearLayout starContainer;
            private TextView ratingCount;

            public GradesViewHolder(View itemView) {
                super(itemView);
                starContainer = itemView.findViewById(R.id.star_container);
                ratingCount = itemView.findViewById(R.id.rating_count);
            }

            public void bind(GradeData gradeData) {
                int roundedRating = Math.round(gradeData.rating);
                starContainer.removeAllViews();

                // Star size
                int starSize = 48; // Size in pixels, can be changed as needed

                // Adding stars
                for (int i = 0; i < 5; i++) {
                    ImageView star = new ImageView(itemView.getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(starSize, starSize);
                    star.setLayoutParams(params);

                    if (i < roundedRating) {
                        star.setImageResource(R.drawable.star_filled); // Use your resource for filled star
                    } else {
                        star.setImageResource(R.drawable.star_empty); // Use your resource for empty star
                    }
                    starContainer.addView(star);
                }

                // Formatting text with rating and number of ratings
                String ratingText = String.format("%.1f", gradeData.rating); // Format float to one decimal place
                String ratingCountText = gradeData.ratingCount + " оценок";
                String finalText = ratingText + "  " + ratingCountText;

                // Set text
                ratingCount.setText(finalText);
            }
        }
    }

    // Your adapter for nameRecyclerView
    public static class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameViewHolder> {

        private ArrayList<String> nameList;

        public NameAdapter(ArrayList<String> nameList) {
            this.nameList = nameList;
        }

        @NonNull
        @Override
        public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_name, parent, false);
            return new NameViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
            String name = nameList.get(position);
            holder.nameTextView.setText(name);
        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }

        public static class NameViewHolder extends RecyclerView.ViewHolder {
            public TextView nameTextView;

            public NameViewHolder(View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.nameTextView); // This should match your item layout
            }
        }
    }

    // Your adapter for addressRecyclerView
    public static class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

        private ArrayList<String> addressList;

        public AddressAdapter(ArrayList<String> addressList) {
            this.addressList = addressList;
        }

        @NonNull
        @Override
        public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
            return new AddressViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
            String address = addressList.get(position);
            holder.addressTextView.setText(address);
        }

        @Override
        public int getItemCount() {
            return addressList.size();
        }

        public static class AddressViewHolder extends RecyclerView.ViewHolder {
            public TextView addressTextView;

            public AddressViewHolder(View itemView) {
                super(itemView);
                addressTextView = itemView.findViewById(R.id.addressTextView); // This should match your item layout
            }
        }
    }

    // Your adapter for categRecyclerView
    public static class CategAdapter extends RecyclerView.Adapter<CategAdapter.CategViewHolder> {

        private ArrayList<String> categList;

        public CategAdapter(ArrayList<String> categList) {
            this.categList = categList;
        }

        @NonNull
        @Override
        public CategViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categ, parent, false);
            return new CategViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CategViewHolder holder, int position) {
            String categ = categList.get(position);
            holder.categTextView.setText(categ);
        }

        @Override
        public int getItemCount() {
            return categList.size();
        }

        public static class CategViewHolder extends RecyclerView.ViewHolder {
            public TextView categTextView;

            public CategViewHolder(View itemView) {
                super(itemView);
                categTextView = itemView.findViewById(R.id.categTextView); // This should match your item layout
            }
        }
    }

    // Data class for working hours
    public static class WorkingHoursData {
        public String hoursText;
        public boolean isOpen;

        public WorkingHoursData(String hoursText, boolean isOpen) {
            this.hoursText = hoursText;
            this.isOpen = isOpen;
        }
    }
}
