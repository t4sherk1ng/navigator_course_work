public class NavigatorImpl implements Navigator {
    private final TreeMap<String, Route> routes;

    public NavigatorImpl() {
        this.routes = new TreeMap<>();
    }

    @Override
    public void addRoute(Route route) {
        if (!routes.containsKey(route.getId())) {
            routes.put(route.getId(), route);
        }
    }

    @Override
    public void removeRoute(String routeId) {
        routes.remove(routeId);
    }

    @Override
    public boolean contains(Route route) {
        for (Route r : getAllRoutes()) {
            if (r.equals(route)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return routes.size();
    }

    @Override
    public Route getRoute(String routeId) {
        return routes.get(routeId);
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = routes.get(routeId);
        if (route != null) {
            route.setPopularity(route.getPopularity() + 1);
        }
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        MyList<Route> result = new MyList<>();

        // Фильтрация маршрутов по начальной и конечной точкам
        for (Route route : getAllRoutes()) {
            MyList<String> points = route.getLocationPoints();
            if (points.size() > 0
                    && points.get(0).equals(startPoint)
                    && points.get(points.size() - 1).equals(endPoint)) {
                result.add(route);
            }
        }

        // Сортировка по критериям:
        // 1. Избранные маршруты выше
        // 2. По возрастанию расстояния
        // 3. По убыванию популярности
        result.sort((r1, r2) -> {
            // Приоритет избранных маршрутов
            if (r1.isFavorite() != r2.isFavorite()) {
                return r1.isFavorite() ? -1 : 1;
            }

            // Сортировка по расстоянию (возрастание)
            int distanceCompare = Double.compare(r1.getDistance(), r2.getDistance());
            if (distanceCompare != 0) {
                return distanceCompare;
            }

            // Сортировка по популярности (убывание)
            return Integer.compare(r2.getPopularity(), r1.getPopularity());
        });

        return result;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        MyList<Route> result = new MyList<>();

        // Фильтрация избранных маршрутов, содержащих destinationPoint не как начальную точку
        for (Route route : getAllRoutes()) {
            MyList<String> points = route.getLocationPoints();
            if (route.isFavorite()
                    && points.contains(destinationPoint)
                    && !points.get(0).equals(destinationPoint)) {
                result.add(route);
            }
        }

        // Сортировка:
        // 1. По расстоянию в порядке возрастания
        // 2. По популярности в порядке убывания
        result.sort((r1, r2) -> {
            int distanceCompare = Double.compare(r1.getDistance(), r2.getDistance());
            if (distanceCompare != 0) {
                return distanceCompare;
            }
            return Integer.compare(r2.getPopularity(), r1.getPopularity());
        });

        return result;
    }

    @Override
    public Iterable<Route> getTop3Routes() {
        MyList<Route> allRoutes = getAllRoutes(); // Получаем все маршруты
        MyList<Route> result = new MyList<>();

        if (allRoutes.size() == 0) {
            return result; // Пустая коллекция, если маршрутов нет
        }

        // Сортировка по критериям:
        // 1. Убывание популярности
        // 2. Возрастание расстояния
        // 3. Возрастание количества точек
        // 4. Сохранение порядка добавления при равенстве всех критериев
        allRoutes.sort((r1, r2) -> {
            // Сравнение по популярности (убывание)
            int popularityCompare = Integer.compare(r2.getPopularity(), r1.getPopularity());
            if (popularityCompare != 0) {
                return popularityCompare;
            }

            // Сравнение по расстоянию (возрастание)
            int distanceCompare = Double.compare(r1.getDistance(), r2.getDistance());
            if (distanceCompare != 0) {
                return distanceCompare;
            }

            // Сравнение по количеству точек (возрастание)
            int pointsCompare = Integer.compare(r1.getLocationPoints().size(), r2.getLocationPoints().size());
            if (pointsCompare != 0) {
                return pointsCompare;
            }

            // Если все критерии равны, сохраняем порядок добавления
            return 0;
        });

        // Возвращаем первые 5 маршрутов (или меньше, если их меньше 5)
        for (int i = 0; i < Math.min(5, allRoutes.size()); i++) {
            result.add(allRoutes.get(i));
        }

        return result;
    }

    private MyList<Route> getAllRoutes() {
        MyList<Route> allRoutes = new MyList<>();
        for (String key : routes.keys()) {
            allRoutes.add(routes.get(key));
        }
        return allRoutes;
    }

    private void sortRoutes(MyList<Route> routes) {
        for (int i = 0; i < routes.size() - 1; i++) {
            for (int j = 0; j < routes.size() - i - 1; j++) {
                Route r1 = routes.get(j);
                Route r2 = routes.get(j + 1);
                if (compareRoutes(r1, r2) > 0) {
                    routes.set(j, r2);
                    routes.set(j + 1, r1);
                }
            }
        }
    }

    private int compareRoutes(Route r1, Route r2) {
        // 1. Избранные маршруты выше
        if (r1.isFavorite() != r2.isFavorite()) {
            return r1.isFavorite() ? -1 : 1;
        }
        // 2. По расстоянию (по убыванию)
        int distanceCompare = Double.compare(r2.getDistance(), r1.getDistance());
        if (distanceCompare != 0) {
            return distanceCompare;
        }
        // 3. По популярности (по убыванию)
        return Integer.compare(r2.getPopularity(), r1.getPopularity());
    }
}