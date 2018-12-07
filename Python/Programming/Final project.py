import random
import turtle


def updateGuess(characters, computer_scientist, total_guessed):
	for guess in total_guessed:
		for idx, ch in enumerate(computer_scientist):
			if ch == guess:
				characters[idx] = guess


def drawGuess(characters):
	x, y = -60, -40
	for ch in characters:
		# draw the char
		if ch:
			turtle.up()
			turtle.setpos(x+13, y-5)
			turtle.down()
			turtle.write(ch, align="center", font=("Arial", 12, "normal"))
		# draw the subline under the char 
		if ch != None:
			turtle.up()
			turtle.setpos(x+3, y-10)
			turtle.down()
			turtle.pensize(2)
			turtle.setpos(x+22, y-10)
		x += 25

def drawAlphabet(char_of_guessed):
	x, y = -220, -150
	turtle.up()
	turtle.setpos(x, y)
	turtle.down()
	turtle.write('Letters:', align="center", font=("Arial", 15, "normal"))

	x, y = -170, -145 
	for ch in alphabet:
		# draw red
		if ch in char_of_guessed:
			turtle.color('red')
		else:
			turtle.color('green')

		turtle.up()
		turtle.setpos(x+13, y-5)
		turtle.down()
		turtle.write(ch, align="center", font=("Arial", 12, "normal"))
		x += 16
	turtle.color('black')

def drawMan(num_of_incorrect_guesses):
	x, y = -160, 120
	# draw head
	if num_of_incorrect_guesses >= 1:
		turtle.up()
		turtle.setpos(x, y)
		turtle.down()
		turtle.circle(30)
	# draw body
	if num_of_incorrect_guesses >= 2:
		turtle.up()
		turtle.setpos(x-30, y)
		turtle.down()
		turtle.setpos(x+30,y)
		turtle.setpos(x+30,y-80)
		turtle.setpos(x-30,y-80)
		turtle.setpos(x-30,y)
	# draw left arm
	if num_of_incorrect_guesses >= 3:
		turtle.up()
		turtle.setpos(x-30, y-10)
		turtle.down()
		turtle.setpos(x-70, y+30)

	# draw right arm
	if num_of_incorrect_guesses >= 4:
		turtle.up()
		turtle.setpos(x+30, y-10)
		turtle.down()
		turtle.setpos(x+70, y+30)
	# draw left leg
	if num_of_incorrect_guesses >= 5:
		turtle.up()
		turtle.setpos(x-20, y-80)
		turtle.down()
		turtle.setpos(x-50, y-160)
	# draw right leg
	if num_of_incorrect_guesses >= 6:
		turtle.up()
		turtle.setpos(x+20, y-80)
		turtle.down()
		turtle.setpos(x+50, y-160)

# judge whether the player is win
def isWin(characters):
	for ch in characters:
		# if the characters contains the '' indicate do not win
		if ch == '':
			return False
	return True


# the list of the computer scientists's names
computer_scientists = ['ALAN TURING', 'DONALD KNUTH', 'ADA LOVELACE', 'GRACE HOPPER', 'GORDON MOORE']
# number of incorrect guesses
num_of_incorrect_guesses = 0
# the alphabet
alphabet = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']



if __name__ == '__main__':
	# hide the turtle
	turtle.hideturtle()
	# the size of the window
	turtle.setup(640, 640)
	# turtle.speed('fast')
	turtle.delay(0)
	# random choice the compter scientist
	computer_scientist = random.choice(computer_scientists)
	# print computer_scientist
	characters = ['' if not ch.isspace() else None for ch in computer_scientist]
	# the total guess char
	total_guessed = set()

	while True:
		turtle.clear()
		updateGuess(characters, computer_scientist, total_guessed)
		drawGuess(characters)
		drawAlphabet(total_guessed)
		drawMan(num_of_incorrect_guesses)

		# win
		if isWin(characters):
			x, y = 0, 220
			turtle.up()
			turtle.setpos(x, y)
			turtle.down()
			turtle.color('green')
			prompt = 'Congratulations!'
			turtle.write(prompt, align="center", font=("Arial", 20, "normal"))
			break

		# game over
		if num_of_incorrect_guesses == 6:
			x, y = 0, 220
			turtle.up()
			turtle.setpos(x, y)
			turtle.down()
			turtle.color('red')
			prompt = 'You lost! The answer was:\n%s' % computer_scientist
			turtle.write(prompt, align="center", font=("Arial", 20, "normal"))
			break

		guess = turtle.textinput("Guess a letter", "Guess a letter")
		# use only the first letter entered
		guess = guess.upper()[0]

		if guess not in total_guessed:# it is a new guess
			total_guessed.add(guess)
			if guess not in computer_scientist: # it's the new wrong guess
				num_of_incorrect_guesses += 1





