import openai
from typing import List
openai.api_key = "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"

def get_api_response(prompt: str) -> str :
    text = None

    try:
        response: dict = openai.Completion.create(
            model='text-davinci-003',
            prompt=prompt,
            temperature=0.9,
            max_tokens=1000,
            top_p=1,
            frequency_penalty=0,
            presence_penalty=0.6,
            stop=[' Human:', ' AI:']
        )

        choices: dict = response.get('choices')[0]
        text = choices.get('text')

    except Exception as e:
        print('ERROR:', e)

    return text


def update_list(message: str, pl: list[str]):
    pl.append(message)


def create_prompt(message: str, pl: list[str]) -> str:
    p_message: str = f'\nHuman: {message}'
    update_list(p_message, pl)
    prompt: str = ''.join(pl)
    return prompt


def get_bot_response(message: str, pl: list[str]) -> str:
    prompt: str = create_prompt(message, pl)
    bot_response: str = get_api_response(prompt)

    if bot_response:
        update_list(bot_response, pl)
        pos: int = bot_response.find('\nAI: ')
        bot_response = bot_response[pos + 5:]
        print(bot_response)
    else:
        bot_response = 'Something went wrong...'

    return bot_response

def fixed(s):
    s = list(s)
    sz = 30
    last = 0
    for i in range(len(s)):
        if i - last > sz:
            j = i
            while s[j] != ' ': j -= 1
            s[j] = '\n'
            last = j
    t = ""
    for i in s: t += i
    return t

def main(message) -> str:
    prompt_list: list[str] = ['Bạn nói tiếng việt và chỉ trả lời bằng tiếng việt',
                              '\nHuman: Mình tên là Long cu bé',
                              '\nAI: Bạn tên là Long cu bé']

    user_input: str = message
    response: str = get_bot_response(user_input, prompt_list)
    respense = fixed(response)
    return (fixed(f' {response}'))
        


