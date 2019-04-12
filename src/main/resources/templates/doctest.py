name: Doctest

def function_to_test(arg: str):
    """
    >>> function_to_test('correct!')
    'correct!'
    """
    return arg


if __name__ == '__main__':
    import doctest

    doctest.testmod()
