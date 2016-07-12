package app.business.utils;


import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;
import app.repository.entities.business.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class TracingLogic {
    static private int INF = Integer.MAX_VALUE / 2;
    static private int n; // количество вершин в орграфе
    static private int m; // количествое дуг в орграфе
    static private ArrayList<Integer> adj[]; // список смежности
    static private ArrayList<Integer> weight[]; // вес ребра в орграфе
    static private boolean used[]; // массив для хранения информации о
    // пройденных и не пройденных вершинах
    static private int dist[]; // массив для хранения расстояния от стартовой
    // вершины
    static private int pred[]; // массив предков, необходимых для восстановления
    // кратчайшего пути из стартовой вершины
    static int startPoint; // стартовая вершина, от которой ищется расстояние до
    // всех
    // других
    private TracingLogic() {
    }



    public static List<Route> defineTrace(Line cable, List<JoinPoint> points, List<Route> routes) {
        setData(cable.getStartPoint(), points, routes);
        dejkstra(startPoint);
        List<Route> trace = new ArrayList<>();
        List<Integer> list = addWay(points.indexOf(cable.getEndPoint()), new ArrayList<>());
        // System.out.println(list);
        for (int i = 0; i < list.size() - 1; i++) {
            JoinPoint thisPoint = points.get(list.get(i));
            JoinPoint nextPoint = points.get(list.get(i + 1));
            for (Route rou : routes) {
                if (thisPoint.equals(rou.getFirstEnd()) && nextPoint.equals(rou.getSecondEnd())) {
                    trace.add(rou);
                    break;
                } else if (nextPoint.equals(rou.getFirstEnd()) && thisPoint.equals(rou.getSecondEnd())) {
                    trace.add(rou);
                    break;
                }
            }
        }
        return trace;
    }

    private static List<Integer> addWay(int v, List<Integer> stock) {
        if (v == -1) {
            return stock;
        }
        addWay(pred[v], stock);
        stock.add(v);
        return stock;
    }

    private static void setData(JoinPoint joinPoint, List<JoinPoint> joinPoints, List<Route> routes) {

        n = joinPoints.size(); // кол-во всех точек, возможных
        // для прохода
        m = routes.size(); // кол-во всех трасс,
        // возможных для прохода

        startPoint = joinPoints.indexOf(joinPoint); // адрес ячейки в
        // массиве,
        // определенный
        // для
        // оборудования,
        // от
        // которого мы
        // ищем
        // путь
        // инициализируем списка смежности графа размерности n
        adj = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            adj[i] = new ArrayList<>();
        }
        // инициализация списка, в котором хранятся веса ребер
        weight = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            weight[i] = new ArrayList<>();
        }
        // считываем граф, заданный списком ребер
        for (int i = 0; i < m; ++i) {
            Route route = routes.get(i);
            int u = joinPoints.indexOf(route.getFirstEnd());
            int v = joinPoints.indexOf(route.getSecondEnd());
            int w = (int) route.getLength();
            adj[u].add(v);
            weight[u].add(w);
            adj[v].add(u);
            weight[v].add(w);

        }
        used = new boolean[n];
        Arrays.fill(used, false);
        pred = new int[n];
        Arrays.fill(pred, -1);
        dist = new int[n];
        Arrays.fill(dist, INF);
    }

    // процедура запуска алгоритма Дейкстры из стартовой вершины
    private static void dejkstra(int s) {
        dist[s] = 0; // кратчайшее расстояние до стартовой вершины равно 0
        for (int iter = 0; iter < n; ++iter) {
            int v = -1;
            int distV = INF;
            // выбираем вершину, кратчайшее расстояние до которого еще не
            // найдено
            for (int i = 0; i < n; ++i) {
                if (used[i]) {
                    continue;
                }
                if (distV < dist[i]) {
                    continue;
                }
                v = i;
                distV = dist[i];
            }
            // рассматриваем все дуги, исходящие из найденной вершины
            for (int i = 0; i < adj[v].size(); ++i) {
                int u = adj[v].get(i);
                int weightU = weight[v].get(i);
                // релаксация вершины
                if (dist[v] + weightU < dist[u]) {
                    dist[u] = dist[v] + weightU;
                    pred[u] = v;
                }
            }
            // помечаем вершину v просмотренной, до нее найдено кратчайшее
            // расстояние
            used[v] = true;
        }
    }
}