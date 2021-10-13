# gameOfLife
game of life made using java
### upd 13.10
1. поле теперь boolean[][]
2. оптимальные настройки для вывода в консоль
3. игра бесконечна за исключением правил игры "Жизнь":
          Игра прекращается, если
на поле не останется ни одной «живой» клетки,
конфигурация на очередном шаге в точности (без сдвигов и поворотов) повторит себя же на одном из более ранних шагов (складывается периодическая конфигурация),
при очередном шаге ни одна из клеток не меняет своего состояния (складывается стабильная конфигурация; предыдущее правило, вырожденное до одного шага назад).
