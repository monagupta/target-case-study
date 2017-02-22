import json
import requests

# Simple End-To-End Test

url = 'https://frozen-citadel-78149.herokuapp.com/products/'

# Tests GET endpoint, specifically fetching the name from redsky
def test_name_fetching():
    id = 13860428
    expected_name = "The Big Lebowski (Blu-ray)"
    r = requests.get(url + str(id))
    if r.json()['name'] != expected_name:
        print("FAILED: test_name_fetching")
        return

    print("passed: test_name_fetching")

# Tests PUT endpoint to update price, test GET endpoint to verify update
def test_update_price():
    id = 1
    value_to_restore = requests.get(url + str(id)).json()['current_price']

    # Update value to 20 FOO
    new_value = {"value": 20, "currency_code": "FOO"}
    headers = {'Content-type': 'application/json'}
    req = requests.put(url + str(id), data=json.dumps(new_value), headers=headers).json()
    if req['current_price'] != new_value:
        print("FAILED: test_update_price")
        return

    # GET to see if value is properly updated
    req = requests.get(url + str(id)).json()
    if req['current_price'] != new_value:
        print("FAILED: test_update_price")
        return

    # Restore original price
    requests.put(url + str(id), data=json.dumps(value_to_restore), headers=headers)
    print("passed: test_update_price")

# Run all tests
test_name_fetching()
test_update_price()
