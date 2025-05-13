main = do
    first <- getLine
    let d = (read first :: Int)

    let valid = [a * 100 + b * 10 + c | a <- [0,1,2], b <- [0,1..9], c <- [0,1..9], a * 100 + b * 10 + c <= 200, isValid(a,b,c)]

    theLoop d valid


-- loop through the inputs n number of times, based on first line of input
--theLoop:: (Num a, Eq a, Int b) => a -> [b] -> IO()
theLoop 0 f = return ()
theLoop n valid = do
    x <- getLine
    let num = (read x :: Int)
    if elem num valid then putStrLn x else getClosest num valid
    theLoop (n-1) valid


-- Check if this number can be in the list of possible keypad combos
isValid:: (Num x, Integral x) => (x, x, x) -> Bool 
isValid (a, b, c)
    | (a == 0) && (b == 0) || b==0 && c==0 = True -- 0-9 and 100, 200 are accepted
    | (b == 0) && (c /= 0) = False -- if second number is 0, and third number is not cannot be zero 10X, are wrong
    | (c < b)  && (c /= 0) = False -- Anywhere when third number is less than first and not zero, it is automatically wrong
    | (mod b 3 == 0) && ((mod c 3 /= 0) || (c == 0)) = False -- removes all incorect from 3X, 6X, 9X, 13X, 16X, 19X

    -- Since a keypad is 3 wide, we can use mod 3 to check if the third character is to the left of the second.
    -- For this to be only the numbers we want, to actually boot out we need for both b and c greater than 0 and c to not be 3, 6, 9 
    -- its weird but if c is 3, 6, 9 it stays. But its correct for numbers such as 13, 16, and 19 lol
    | ((compareMods (b, c, 3)) && (b > 0)) && (c > 0) && mod c 3 /= 0 = False
    | otherwise = True -- accept all numbers that pass


-- Comparision method to help keep lines shorter
compareMods:: (Num x, Ord x, Integral x) => (x, x, x) -> Bool
compareMods (a, b, m) 
    | (mod a m) > (mod b m) = True
    | otherwise = False


-- if not exact then get the closest to the keypad number
getClosest:: Int -> [Int] -> IO()
getClosest x valids = do

    let pairs = [(abs (a - x),a) | a <- valids]
    let close = minimumsFst pairs
    let out = snd (close !! 0)
    print out


minimumsFst :: Ord a => [(a, b)] -> [(a, b)]
minimumsFst [] = [] -- empty list of 2-tuple

-- search for the smallest first index of a list of tuples 
minimumsFst list = filter ((==) small . fst) list
    where small = minimum (map fst list)

-- given our input list, xs we call filter which allows us to check each first
-- variable in the list of tuples
-- minfst is the minumim of first (fst) indexes in the list xs
-- not recursive, O(n) time