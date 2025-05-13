# import numpy.random.rand as rand
from numpy.random import rand


def main():
    # Initialize variables
    trials = 10000
    wins = 0
    iterations = []

    # For each trial
    for i in range(trials):
        # run the trial
        iHaveMoney, time = casino()
        iterations.append(time)
        if iHaveMoney:
            wins += 1

        # Print when 1000 trials have completed
        if i % 1000 == 0:
            print(f"{i} trials completed")

    # Print probabilities
    print(f'The exact probability of leaving with $150 is {(((0.51/0.49)**100)-1) / (((0.51/0.49)**150)-1):0.4f}')
    print(f'I won {wins} times out of {trials}, which is {wins*100/trials:0.2f}%')

    print(f'Using, the exact formula it should take {((100 -(150 * (1-((0.51/0.49)**100)))/(1-((0.51/0.49)**150))) ) / (0.51-0.49):0.4f} minutes')
    print(f'On average it took me {sum(iterations) / len(iterations):0.2f} minutes to leave the table')


def casino():
    # Starting cash and iteration count
    cash = 100
    count = 0
    while checker(cash):
        # Get a random number between 0 and 1
        rNum = rand(1,1)[0][0]

        if rNum < 0.49:
            cash += 1

        else:
            cash -= 1
        count += 1
    # After trial ends if cash is zero, you lost if not you won!!
    if cash == 0:
        return False, count    
    return True, count

def checker(amount):
    '''
        This function returns a boolean based on whether the
        given parameter, amount, is equal to or above 150 or
        less than or equal to 0
    ''' 

    if amount >= 150:
        return False
    elif amount <= 0:
        return False
    return True


main()