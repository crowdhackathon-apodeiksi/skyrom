package skyrom.com.taxmachine;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class DummyProvider {

    public static List<Draw> getMyDraws(Context context) {
        List<Draw> drawList = new ArrayList<>();
        Draw draw = new Draw();
        draw.setDatetime("16/12/2015 17:00");
        draw.setName("Διαγωνισμός για μια τσάντα θαλάσσης");
        draw.setPrice("Τσάντα θαλάσσης");
        draw.setShopName("CycleLand");
        draw.setImage(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bicycle));
        drawList.add(draw);

        draw = new Draw();
        draw.setDatetime("16/10/2015 09:00");
        draw.setName("Διαγωνισμός για ένα εισητήριο για Αμερική");
        draw.setPrice("Αεροπορικο εισητήριο");
        draw.setShopName("TravelCity 24");
        draw.setImage(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.apple));
        drawList.add(draw);

        draw = new Draw();
        draw.setDatetime("10/12/2015 19:00");
        draw.setName("Διαγωνισμός για ένα πανέμορφο ζευγάρι γυαλιών ηλίου");
        draw.setPrice("Γυαλιά Ηλίου");
        draw.setShopName("SunGlasses 365");
        draw.setImage(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.glasses));
        drawList.add(draw);

        draw = new Draw();
        draw.setDatetime("30/10/2015 21:45");
        draw.setName("Διαγωνισμός για ένα πανάκριβο κόσμημα");
        draw.setPrice("Περιδέραιο");
        draw.setShopName("Jewel VIP");
        draw.setImage(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.nice));
        drawList.add(draw);

        return drawList;
    }

    public static List<Draw> getDraws(Context context) {
        List<Draw> drawList = new ArrayList<>();
        Draw draw = new Draw();
        draw.setDatetime("16/12/2015 17:00");
        draw.setName("Διαγωνισμός για μια τσάντα θαλάσσης");
        draw.setPrice("Τσάντα θαλάσσης");
        draw.setShopName("CycleLand");
        draw.setImage(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bicycle));
        drawList.add(draw);

        draw = new Draw();
        draw.setDatetime("30/10/2015 21:45");
        draw.setName("Διαγωνισμός για ένα πανάκριβο κόσμημα");
        draw.setPrice("Περιδέραιο");
        draw.setShopName("Jewel VIP");
        draw.setImage(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.nice));
        drawList.add(draw);

        return drawList;
    }
}
