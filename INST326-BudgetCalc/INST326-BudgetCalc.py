import re
import csv

class Budget:
    """
    The Budget class represents a budgeting system that helps users
    manage their finances.
    """

    def validate_file_path(self, path):
        """
        Validate if the provided path follows a valid file path pattern.
        Parameters:
        - path (str): The file path to be validated.
        Returns:
        - bool: True if the path is valid, False otherwise.
        """
        pattern = r'^[a-zA-Z]:\\(?:[^\\/:*?"<>|\r\n]+\\)*[^\\/:*?"<>|\r\n]*$'
        return re.match(pattern, path) is not None

    def get_csv_filename(self):
        """
        Prompt the user to enter the path to a CSV file and validate it.
        Returns:
        - str: The valid path to the CSV file.
        """
        while True:
            csv_filename = input("Enter the path to the CSV file: ")
            if self.validate_file_path(csv_filename):
                print("\nValid file path!\n")
                return csv_filename
            else:
                print("Invalid file path. Please enter a valid path.")

    def get_positive_integer_input(self, prompt, valid_options=None):
        """
        Prompt the user to enter a positive integer and validate it.
        Parameters:
        - prompt (str): The prompt message for the user.
        - valid_options (list, optional): A list of valid options for the input.
        Returns:
        - int: The valid positive integer.
        """
        while True:
            try:
                value = input(prompt)
                if value.lower() == 'end':
                    raise KeyboardInterrupt("Program terminated by user.")
                value = int(value)
                if valid_options is None or value in valid_options:
                    return value
                else:
                    print(f"\nPlease choose one of the valid options: {valid_options}\n")
            except ValueError:
                print("\nInvalid input. Please enter a valid response.\n")

    def get_percentage_input(self, prompt, valid_options=None):
        """
        Prompt the user to enter a percentage and validate it.
        Parameters:
        - prompt (str): The prompt message for the user.
        - valid_options (list, optional): A list of valid options for the input.
        Returns:
        - str: The valid percentage input.
        """
        while True:
            input_str = input(prompt)
            if input_str.lower() == 'end':
                raise KeyboardInterrupt("Program terminated by user.")
            try:
                if input_str == '<10':
                    return '10'
                elif input_str == '>70':
                    return '70'
                if valid_options and input_str not in valid_options:
                    raise ValueError("\nInvalid input. Please choose one of the valid options.\n")
                return input_str
            except ValueError as e:
                print(e)

    def create_budget(self):
        """
        Create a budget based on user input for income, savings, expenses, etc.
        Returns:
        - tuple: A tuple containing budget information and the CSV filename.
        """
        try:
            monthly = self.get_positive_integer_input(
                "What's your monthly income? (in whole thousands). Ex. 1000, 10000 "
            )
            leftovers = monthly
            print(f"\nYou have ${round(leftovers, 2)} left this month. Keep going.\n")

            savings_percent = self.get_percentage_input(
                "How much do you save every month as a percentage of your monthly? "
                "Choose one: <10, 20, 30, 40, 50, 60, >70 ",
                valid_options=["<10", "20", "30", "40", "50", "60", ">70"]
            )
            savings = monthly * float(savings_percent) / 100
            leftovers -= savings
            if leftovers <= 0:
                raise ValueError("\nError: You have no money left.\n")
            print(f"You have ${round(leftovers, 2)} left this month. Keep going.\n")

            retirement_percent = self.get_percentage_input(
                "How much do you save in retirement every month as a percentage of your monthly? "
                "Choose one: <10, 20, 30, 40, 50, 60, >70 ",
                valid_options=["<10", "20", "30", "40", "50", "60", ">70"]
            )
            retirement = monthly * float(retirement_percent) / 100
            leftovers -= retirement
            if leftovers <= 0:
                raise ValueError("\nError: You have no money left.\n")
            print(f"You have ${round(leftovers, 2)} left this month. Keep going.\n")

            print("Here come the taxes! Yay! 20% income tax ")
            leftovers -= monthly * 0.20
            if leftovers <= 0:
                raise ValueError("\nError: You have no money left.\n")
            print(f"You have ${round(leftovers, 2)} left this month. Keep going.\n")

            kids = self.get_positive_integer_input(
                "How many kids do you have under 18? 300 dollars per kid for food monthly (average). "
            )
            leftovers -= kids * 300
            if leftovers <= 0:
                raise ValueError("\nError: You have no money left.\n")
            print(f"You have ${round(leftovers, 2)} left this month. Keep going.\n")

            housing = self.get_positive_integer_input(
                "How much do you pay monthly for rent/mortgage? Estimate. "
            )
            leftovers -= housing
            if leftovers <= 0:
                raise ValueError("\nError: You have no money left.\n")
            print(f"You have ${round(leftovers, 2)} left this month. Keep going.\n")

            miles = self.get_positive_integer_input(
                "How many miles do you drive a month? Average 3 dollars a gallon and 35mpg. "
            )
            gas = (miles / 35) * 3
            leftovers -= gas
            if leftovers <= 0:
                raise ValueError("\nError: You have no money left.\n")
            print(f"You have ${round(leftovers, 2)} left this month. Keep going.\n")

            self.budget_info = {
                'Remaining Budget': leftovers,
                'Monthly Income': monthly,
                'Taxes': monthly * 0.20,
                'Kids Expenses': kids * 300,
                'Housing Expenses': housing,
                'Gas Expenses': gas,
                'Savings': savings,
                'Retirement Savings': retirement,
            }

            print(f"Done! You have ${round(leftovers, 2)} left to spend on things you'd like every month.\n")
            csv_filename = self.get_csv_filename()
            return self.budget_info, csv_filename

        except ValueError as e:
            print(e)
            return None, None
        except KeyboardInterrupt as e:
            print(e)
            return None, None
        except Exception as e:
            print(f"An error occurred: {e}")
            print("Please ensure you enter valid inputs.")
            return None, None


class LeisureSpending:
    """
    The LeisureSpending class manages leisure activities
    and compares spending to the budget.
    """

    def __init__(self, budget_info):
        self.activities = {}
        self.total_spending = 0
        self.budget_info = budget_info

    def read_csv(self, filename):
        try:
            with open(filename, 'r') as file:
                reader = csv.DictReader(file)
                for row in reader:
                    category = row.get('leisure activity')
                    amount = float(row.get('total', 0))
                    if category not in self.activities:
                        self.activities[category] = 0
                    self.activities[category] += amount
                    self.total_spending += amount
        except FileNotFoundError:
            print(f"Error: File '{filename}' not found.")
        except Exception as e:
            print(f"An error occurred while reading the CSV file: {e}")

    def compare_spending_to_budget(self):
        try:
            remaining_budget = self.budget_info.get('Remaining Budget', 0)
            if remaining_budget < self.total_spending:
                print(f"Warning: Your total leisure spending this month "
                      f"(${self.total_spending:.2f}) exceeds the remaining budget "
                      f"(${remaining_budget:.2f}).\n")
            else:
                print(f"Great job! You have ${remaining_budget - self.total_spending:.2f} "
                      f"left for leisure activities this month.\n")
        except Exception as e:
            print(f"An error occurred while comparing spending to budget: {e}")

    def print_leisure_activities(self):
        sorted_activities = sorted(self.activities.items(), key=lambda x: x[1], reverse=True)
        print("Leisure Activities and Spending:")
        for activity, spending in sorted_activities:
            print(f"{activity}: ${spending:.2f}")


def main():
    try:
        budget = Budget()
        budget_info, csv_filename = budget.create_budget()
        if budget_info is None or csv_filename is None:
            print("Error: Unable to proceed without valid budget information and CSV filename.")
            return

        leisure_spending = LeisureSpending(budget_info)
        leisure_spending.read_csv(csv_filename)
        leisure_spending.compare_spending_to_budget()
        print(f"\nTotal spending on leisure activities for the month: ${leisure_spending.total_spending:.2f}\n")
        leisure_spending.print_leisure_activities()

    except ValueError as e:
        print(e)
    except KeyboardInterrupt as e:
        print(e)
    except Exception as e:
        print(f"An error occurred: {e}")
        print("Please ensure you enter valid inputs.")


if __name__ == "__main__":
    main()
