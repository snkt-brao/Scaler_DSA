package IntroDSA.FoodRatings;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class FoodRatings {

    private Map<String, Integer> foodRatingMap = new HashMap<>();

    private Map<String, String> foodCuisineMap = new HashMap<>();

    private Map<String, TreeSet<Pair<Integer, String>>> cuisineFoodMap = new HashMap<>();

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        for (int i = 0; i < foods.length; ++i) {
            foodRatingMap.put(foods[i], ratings[i]);
            foodCuisineMap.put(foods[i], cuisines[i]);

            cuisineFoodMap.computeIfAbsent(cuisines[i], k -> new TreeSet<>((a, b) -> {
                int compareByRating = Integer.compare(a.getKey(), b.getKey());
                if (compareByRating == 0) {
                    // If ratings are equal, compare by food name (in ascending order).
                    return a.getValue().compareTo(b.getValue());
                }
                return compareByRating;
            })).add(new Pair(-ratings[i], foods[i]));
        }
    }

    public void changeRating(String food, int newRating) {
        String cuisineName = foodCuisineMap.get(food);

        TreeSet<Pair<Integer, String>> cuisineSet = cuisineFoodMap.get(cuisineName);
        Pair<Integer, String> oldElement = new Pair<>(-foodRatingMap.get(food), food);
        cuisineSet.remove(oldElement);

        foodRatingMap.put(food, newRating);
        cuisineSet.add(new Pair<>(-newRating, food));
    }

    public String highestRated(String cuisine) {
        Pair<Integer, String> highestRated = cuisineFoodMap.get(cuisine).first();
        return highestRated.getValue();
    }


    static class Pair<T1, T2> {
        T1 key;
        T2 value;

        public Pair(T1 key, T2 value) {
            this.key = key;
            this.value = value;
        }

        public T1 getKey() {
            return key;
        }

        public T2 getValue() {
            return value;
        }
    }
}