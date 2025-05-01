"""
soundex_fst.py

Finite State Transformer implementation of the Soundex algorithm.

The Soundex algorithm is a phonetic algorithm for indexing names by sound, as pronounced in English.
The goal is for homophones to be encoded to the same representation so that they can be matched despite minor differences in spelling.

"""

class Soundex:
    def __init__(self):
        self.name = ""

    def __init__(self, name: str):
        self.name = name

    def keep_first_and_drop_vowels(self) -> str:
        """
        Keep the first letter and drop all occurrences of 
        non-initial a, e, h, i, o, u, w, y.
        """
        if not self.name:
            return ""
        first_letter = self.name[0]
        rest = self.name[1:]
        to_drop = set("aehiouwyAEHIOUWY")
        rest_filtered = "".join([c for c in rest if c not in to_drop])
        return first_letter + rest_filtered

    def take_consonants_and_give_numbers(self, consonant: str) -> str:
        """
        Take consonants and give numbers according to Soundex rules.
        """
        if not consonant:
            return ""
        if consonant in set("bfpvBFPV"):
            return "1"
        elif consonant in set("cgjkqsxzCGJKQSXZ"):
            return "2"
        elif consonant in set("dtDT"):
            return "3"
        elif consonant in set("lL"):
            return "4"
        elif consonant in set("mnMN"):
            return "5"
        elif consonant in set("rR"):
            return "6"
        else:
            return consonant

    def replace_letters_with_numbers(self) -> str:
        """
        Replace letters with numbers according to Soundex rules.
        """
        if not self.name:
            return ""

        first_letter = self.name[0]
        rest = self.name[1:]
        rest_filtered = "".join([self.take_consonants_and_give_numbers(c) for c in rest])
        return first_letter + rest_filtered
    
    def collapse_consecutive_digits(self) -> str:
        """
        Collapse consecutive digits.
        """
        if not self.name:
            return ""
        first_letter = self.name[0]
        rest = self.name[1:]
        rest_filtered = ""
        for i in range(len(rest)):
            if rest[i].isdigit() and rest[i] == rest[i-1]:
                continue
            else:
                rest_filtered += rest[i]
        return first_letter + rest_filtered

    def pad_with_zeros(self,name: str) -> str:
        """
        Pad with zeros to make the length 4.
        """
        if not name:
            return ""
        if len(name) > 4:
            return name[:4]
        else:
            return name + "0" * (4 - len(name))
        
    def soundex_fst(self) -> str:
        """
        Soundex FST.
        """
        if not self.name:
            return ""
        self.name = self.name.upper()
        self.name = self.keep_first_and_drop_vowels()
        self.name = self.replace_letters_with_numbers()
        self.name = self.collapse_consecutive_digits()
        self.name = self.pad_with_zeros(self.name)
        return self.name


if __name__ == "__main__":
    print("\033[95m" + "="*40 + "\033[0m")
    print("\033[94mWelcome to the Soundex Encoder!\033[0m")
    print("\033[95m" + "="*40 + "\033[0m")
    while True:
        name = input("\033[92mEnter a name to continue or type 'exit' to quit: \033[0m")
        if name == "exit":
            print("\033[91mExiting... Goodbye!\033[0m")
            print("\033[95m" + "="*40 + "\033[0m")
            break
        else:
            soundex = Soundex(name)
            print(f"\033[93mSoundex code of the name: \033[1m{soundex.soundex_fst()}\033[0m")
            print("\033[95m" + "-"*40 + "\033[0m")