import json
from urllib import request, parse

with open('log.json', encoding='utf-8', mode='w') as file:
    file.write('')


def import_data(string):
    data = bulk_string.encode("UTF-8")
    req = request.Request(f"http://localhost:9200/tweets/_bulk",
                          data=data,
                          headers={"Content-Type": "application/json; charset=utf-8"})
    resp = request.urlopen(req)
    with open('log.json', encoding='utf-8', mode='a') as file:
        file.write(resp.read().decode("UTF-8"))


create_string = '{ "create" : {} }\n'
bulk_string = ''
counter = 0
with open('data-5k.json', encoding='utf-8') as file:
    for line in file:
        counter += 1
        bulk_string = bulk_string + \
            f'{{\"index\":{{"_id": "{counter}" }}}}' + "\n" + line + "\n"
        if (counter % 1000) == 0:
            import_data(bulk_string)
            bulk_string = ''
            print(counter, ' ', end='', flush=True)

    if bulk_string != '':
        import_data(bulk_string)
