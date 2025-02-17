public class Main {
    public static void main(String[] args) {
        // Создаем объект навигатора
        Navigator navigator = new NavigatorImpl();

        // Создаем маршруты
        MyList<String> points1 = new MyList<>();
        points1.add("A");
        points1.add("B");
        points1.add("C");
        Route route1 = new Route("1", 100.0, 10, true, points1); // Избранный, расстояние 100.0, популярность 10

        MyList<String> points2 = new MyList<>();
        points2.add("A");
        points2.add("D");
        points2.add("C");
        Route route2 = new Route("2", 150.0, 5, false, points2); // Не избранный, расстояние 150.0, популярность 5

        MyList<String> points3 = new MyList<>();
        points3.add("A");
        points3.add("E");
        points3.add("F");
        points3.add("C");
        Route route3 = new Route("3", 120.0, 8, true, points3); // Избранный, расстояние 120.0, популярность 8

        // Добавляем маршруты в навигатор
        navigator.addRoute(route1);
        navigator.addRoute(route2);
        navigator.addRoute(route3);

        System.out.println("Маршруты успешно добавлены в навигатор.");

        // Поиск маршрутов от точки "A" до точки "C"
        System.out.println("\nПоиск маршрутов от точки 'A' до точки 'C':");
        Iterable<Route> searchResult = navigator.searchRoutes("A", "C");
        for (Route route : searchResult) {
            System.out.println("Маршрут " + route.getId() + ": Расстояние = " + route.getDistance() +
                    ", Популярность = " + route.getPopularity() +
                    ", Избранный = " + route.isFavorite());
        }

        // Получение избранных маршрутов, содержащих точку "C" (не начальную)
        System.out.println("\nИзбранные маршруты, содержащие точку 'C':");
        Iterable<Route> favoriteRoutes = navigator.getFavoriteRoutes("C");
        for (Route route : favoriteRoutes) {
            System.out.println("Маршрут " + route.getId() + ": Расстояние = " + route.getDistance() +
                    ", Популярность = " + route.getPopularity());
        }

        // Получение топ-5 маршрутов
        System.out.println("\nТоп-5 маршрутов:");
        Iterable<Route> topRoutes = navigator.getTop3Routes();
        for (Route route : topRoutes) {
            System.out.println("Маршрут " + route.getId() + ": Популярность = " + route.getPopularity() +
                    ", Расстояние = " + route.getDistance() +
                    ", Количество точек = " + route.getLocationPoints().size());
        }

        // Увеличение популярности маршрута "2"
        System.out.println("\nУвеличение популярности маршрута '2'...");
        navigator.chooseRoute("2");

        // Повторный поиск маршрутов от точки "A" до точки "C" после увеличения популярности
        System.out.println("\nПовторный поиск маршрутов от точки 'A' до точки 'C':");
        searchResult = navigator.searchRoutes("A", "C");
        for (Route route : searchResult) {
            System.out.println("Маршрут " + route.getId() + ": Расстояние = " + route.getDistance() +
                    ", Популярность = " + route.getPopularity() +
                    ", Избранный = " + route.isFavorite());
        }
    }
}