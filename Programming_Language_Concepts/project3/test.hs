

mergesort:: (Ord a) => [a] -> [a]
mergesort [] = []
mergesort [x] = [x]
mergesort xs = merge (mergesort l) (mergesort r)
    where l = take (length xs `div` 2) xs
          r = drop (length xs `div` 2) xs



merge:: (Ord a) => [a] -> [a] -> [a]
merge (l:ls) (r:rs)
    | l < r = l:merge ls (r:rs)
    | otherwise = r:merge (l:ls) rs
merge [] r = r
merge l [] = l


hanoi:: Integer -> String

hanoi n = solveHanoi n "1" "2" "3"


solveHanoi:: Integer -> String -> String -> String -> String
solveHanoi 1 a b c = "Move Disc 1 from Peg " ++ a ++ " to Peg " ++ c ++ "\n"

solveHanoi n a b c = first ++ move ++ end
    where first = solveHanoi (n-1) a c b
          move = "Move Disc " ++ show n ++ "from Peg " ++ a ++ " to Peg " ++ c ++ "\n"
          end = solveHanoi (n-1) b a c

