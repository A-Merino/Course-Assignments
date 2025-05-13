class Doctor():
    def __init__(self, db_list):
        self._first_name = ""
        self._last_name = ""
        self._phone = ""
        self._facility = ""
        self._address = ""
        self._full_name = db_list[0]
        self._specialty = db_list[1]
        self._gender = db_list[4]
        self.config_names(db_list[0])
        self.config_rest(db_list[2])


    def config_names(self, full):
        self._full_name = full
        name = full.split(",")[0]
        singular = name.split(" ")
        self._first_name = singular[0]
        if len(singular) == 2:
            self._last_name = singular[1]
        elif len(singular) == 4:
            self._last_name = singular[3]
        else:
            self._last_name = singular[2]

    def config_rest(self, loc):
        sep = loc.split(", ")
        self._phone = sep[-1]
        self._facility = sep[0]
        if len(sep) == 7:
            self._address = "\n".join((sep[2], sep[3],sep[4],sep[5],))
        else:
            self._address = "\n".join((sep[1], sep[2],sep[3],sep[4],))

    def get_first_name(self):
        return self._first_name

    def get_last_name(self):
        return self._last_name

    def get_full_name(self):
        return self._full_name

    def get_phone(self):
        return self._phone

    def get_facility(self):
        return self._facility

    def get_address(self):
        return self._address

    def get_specialty(self):
        return self._specialty

    def get_gender(self):
        return self._gender