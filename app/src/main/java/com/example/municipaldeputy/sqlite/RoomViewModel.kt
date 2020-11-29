package com.example.municipaldeputy.sqlite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.municipaldeputy.entity.*
import com.example.municipaldeputy.sqlite.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val regionRepository: RegionRepository
    private val districtRepository: DistrictRepository
    private val streetRepository: StreetRepository
    private val houseRepository: HouseRepository
    private val photoRepository: PhotoRepository
    private val workRepository: WorkRepository
    private val activeRepository: ActiveRepository

    init {
        val database = RoomDB.getDatabase(application)
        regionRepository = RegionRepository(database.regionDao())
        districtRepository = DistrictRepository(database.districtDao())
        streetRepository = StreetRepository(database.streetDao())
        houseRepository = HouseRepository(database.houseDao())
        photoRepository = PhotoRepository(database.photoDao())
        workRepository = WorkRepository(database.workDao())
        activeRepository = ActiveRepository(database.humanDao())
        if (checkRegionEmpty()) {//TODO remove while have a data sample
            addDebugList()
        }
    }

    fun checkRegionEmpty(): Boolean {//TODO remove while have a data sample
        return regionRepository.readAllDataSync().isEmpty()
    }

    //region
    fun getRegionData() =
        regionRepository.readAllData()

    suspend fun addRegion(region: Region): Long {
        return regionRepository.addRegion(region)
    }

    fun getRegionIdByName(regionName: String) =
        regionRepository.getIdByName(regionName)

    fun getAllDataSync() = regionRepository.readAllDataSync()

    //district
    fun getDistrictData() =
        districtRepository.readAllData()

    suspend fun addDistrict(district: District) = districtRepository.addDistrict(district)

    fun getDistrictByRegionId(refionId: Int) = districtRepository.readDataWithRegionId(refionId)

    fun getDistrictIdByName(districtName: String) =
        districtRepository.getIdByName(districtName)

    //street
    fun getStreetData() =
        streetRepository.readAllData()

    suspend fun addStreet(street: Street) = streetRepository.addStreet(street)

    fun getStreetWithDistrictId(districtId: Int) =
        streetRepository.readDataWithDistrictId(districtId)

    fun getStreetIdByName(streetName: String) =
        streetRepository.getIdByName(streetName)

    //house
    fun getHouseData() =
        houseRepository.readAllData()

    fun getHouseWithStreetId(streetId: Int) = houseRepository.readDataWithStreetId(streetId)

    suspend fun addHouse(house: House) = houseRepository.addHouse(house)

    fun getHouseIdByName(houseAddress: String) =
        houseRepository.getIdByName(houseAddress)

    //photo
    fun getPhotoDataSync() =
        photoRepository.readAllData()

    suspend fun addPhoto(photoLink: PhotoLink) =
            photoRepository.addPhoto(photoLink)

    fun getPhotoWithHouseIdSync(houseId: Int) =
        photoRepository.getDataWithHouseIdSync(houseId)

    //work
    fun getWorkData() =
        workRepository.readAllData()

    suspend fun addWork(work: Work) =
            workRepository.addWork(work)

    fun getUndoneDataWithHouseId(houseId: Int) = workRepository.readUndoneDataWithHouseID(houseId)

    fun getDoneDataWithHouseId(houseId: Int) = workRepository.readDoneDataWithHouseID(houseId)

    //active
    fun getActiveData() =
        activeRepository.readAllData()

    fun getActiveDataByHouseId(id: Int) = activeRepository.readDataWithHouseId(id)

    suspend fun addActive(human: Human) =
            activeRepository.addActive(human)

    private fun addDebugList() {
        viewModelScope.launch(Dispatchers.IO) {
            //районы
            regionRepository.addRegion(Region(0, "Авиастроительный"))
            regionRepository.addRegion(Region(0, "Вахитовский"))
            regionRepository.addRegion(Region(0, "Кировский"))
            regionRepository.addRegion(Region(0, "Ново-Совиновский"))
            regionRepository.addRegion(Region(0, "Приволжский"))
            regionRepository.addRegion(Region(0, "Советский"))
            regionRepository.addRegion(Region(0, "Московский"))

            //округа
            districtRepository.addDistrict(
                District(
                    0,
                    "Северный одномандатный избирательный округ №1",
                    1
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Караваевский одномандатный избирательный округ №2",
                    1
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Академический одномандатный избирательный округ №3",
                    2
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Кремлевский одномандатный избирательный округ №4",
                    2
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Пороховой одномандатный избирательный округ №5",
                    3
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Прибрежный одномандатный избирательный округ №6",
                    3
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Декабристский одномандатный избирательный округ №7",
                    3
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Гагаринский одномандатный избирательный округ №8",
                    7
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Тасмовский одномандатный избирательный округ №9",
                    7
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Октябрьский одномандатный избирательный округ №10",
                    7
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Амирхановский одномандатный избирательный округ №11",
                    4
                )
            )
            districtRepository.addDistrict(
                District(
                    0,
                    "Чуйковский одномандатный избирательный округ №12",
                    4
                )
            )

            //улицы
            streetRepository.addStreet(Street(0, "Гудовцева", 1))
            streetRepository.addStreet(Street(0, "Камчатская", 1))
            streetRepository.addStreet(Street(0, "Керченская", 1))
            streetRepository.addStreet(Street(0, "Колхозная", 2))
            streetRepository.addStreet(Street(0, "Колхозная 2-я", 2))
            streetRepository.addStreet(Street(0, "Лянгузова", 2))
            streetRepository.addStreet(Street(0, "Малая Заречная", 3))
            streetRepository.addStreet(Street(0, "Ново-Караваевская", 3))
            streetRepository.addStreet(Street(0, "Односторонняя Луговая", 3))
            streetRepository.addStreet(Street(0, "Песочная", 3))
            streetRepository.addStreet(Street(0, "Петра Баранова", 4))

            //дома, информация ошибочна, начиная отсюда
            houseRepository.addHouse(House(0, "улица Гудовцева №1", 3, 5, "Ук \"Пжкх\"", 1))
            houseRepository.addHouse(House(0, "улица Гудовцева №2", 4, 9, "Ук \"Пжкх\"", 1))
            houseRepository.addHouse(House(0, "улица Гудовцева №2a", 2, 10, "ТСЖ \"Гудок\"", 1))
            houseRepository.addHouse(
                House(
                    0,
                    "улица Камчатская №1",
                    3,
                    5,
                    "Ук \"Жкх Танкодром\"",
                    2
                )
            )
            houseRepository.addHouse(House(0, "улица Камчатская №2", 3, 5, "Ук \"Свобода\"", 2))
            houseRepository.addHouse(House(0, "улица Камчатская №3", 3, 5, "Ук \"Свобода\"", 2))
            houseRepository.addHouse(House(0, "улица Керченская №1", 2, 6, "Ук \"Пжкх\"", 3))
            houseRepository.addHouse(
                House(
                    0,
                    "улица Керченская №2",
                    4,
                    7,
                    "ТСЖ \"Рога и копыта\"",
                    3
                )
            )
            houseRepository.addHouse(
                House(
                    0,
                    "улица Керченская №3",
                    2,
                    8,
                    "Ук \"Жкх Танкодром\"",
                    3
                )
            )
            houseRepository.addHouse(
                House(
                    0,
                    "улица Керченская №3а",
                    6,
                    9,
                    "Ук \"Жкх Танкодром\"",
                    3
                )
            )
            houseRepository.addHouse(
                House(
                    0,
                    "улица Колхозная №2а",
                    1,
                    1,
                    "ТСЖ \"Воля народа\"",
                    4
                )
            )

            //фото
            photoRepository.addPhoto(PhotoLink(0, "house_1", 1, 1))
            photoRepository.addPhoto(PhotoLink(0, "house_2", 1, 1))
            photoRepository.addPhoto(PhotoLink(0, "house_3", 1, 1))
            photoRepository.addPhoto(PhotoLink(0, "house_4", 1, 2))
            photoRepository.addPhoto(PhotoLink(0, "house_5", 1, 2))
            photoRepository.addPhoto(PhotoLink(0, "house_6", 1, 2))
            photoRepository.addPhoto(PhotoLink(0, "house_1", 1, 3))
            photoRepository.addPhoto(PhotoLink(0, "house_3", 1, 3))
            photoRepository.addPhoto(PhotoLink(0, "house_5", 1, 3))
            photoRepository.addPhoto(PhotoLink(0, "house_2", 1, 4))
            photoRepository.addPhoto(PhotoLink(0, "house_4", 1, 4))

            //сторонники
            activeRepository.addActive(
                Human(
                    0,
                    "Мангушева",
                    "Алина",
                    "Раисовна",
                    42,
                    "+791443582142",
                    "mangusheva@kstu.ru",
                    1
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Герасимов",
                    "Александр",
                    "Викторович",
                    44,
                    "+797747882142",
                    "sasha_gv@mail.ru",
                    1
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Кирпичников",
                    "Александр",
                    "Петрович",
                    67,
                    "+797743582142",
                    "A_petrovich@mail.ru",
                    1
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Нгуен",
                    "Нан",
                    "Ан",
                    44,
                    "897743583142",
                    "nguen_nan_an@gmail.ru",
                    1
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Гурьянов",
                    "Владимир",
                    "Владимирович",
                    34,
                    "893743583142",
                    "v_v_gurian@mail.ru",
                    2
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Люшиковский",
                    "Петр",
                    "Доминович",
                    12,
                    "892743585142",
                    "petr_l89@gmail.com",
                    2
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Абануб",
                    "Халаф",
                    "Манур",
                    144,
                    "+793749275632",
                    "AHManur@yahoo.com",
                    2
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Ватисов",
                    "Дюма",
                    "Сергеевич",
                    37,
                    "+712291111234",
                    "vatabeS64@gmail.com",
                    2
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Цинь",
                    "Шихуанди",
                    "-",
                    35,
                    "+711691111234",
                    "chin_kingdom@china.ch",
                    3
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Кузьмин",
                    "Елисей",
                    "Дмитриевич",
                    52,
                    "+711112111234",
                    "e_kuzma@mail.ru",
                    3
                )
            )
            activeRepository.addActive(
                Human(
                    0,
                    "Куликов",
                    "Назарий",
                    "Егорович",
                    22,
                    "+711111111234",
                    "kulikov29@gmail.com",
                    3
                )
            )

            //работы
            workRepository.addWork(Work(0, "Капитальный ремонт", "1.01.1970", "УК \"Пжкх\"", 1, 1))
            workRepository.addWork(
                Work(
                    0,
                    "Замена водопроводных труб",
                    "2.10.2020",
                    "СК БРИЗ",
                    1,
                    1
                )
            )
            workRepository.addWork(Work(0, "Ремонт облицовки", "01.01.2017", "ГК Профит", 0, 1))
            workRepository.addWork(
                Work(
                    0,
                    "Ремонт пожарной лестницы",
                    "15.03.2018",
                    "УК \"Пжкх\"",
                    0,
                    1
                )
            )
            workRepository.addWork(
                Work(
                    0,
                    "Установка общедомовых счётчиков",
                    "11.11.2015",
                    "ГК Профит",
                    0,
                    1
                )
            )
            workRepository.addWork(Work(0, "Капитальный ремонт", "1.10.2020", "УК \"Пжкх\"", 0, 2))
            workRepository.addWork(
                Work(
                    0,
                    "Ремонт детской площадки",
                    "1.04.2001",
                    "Унистрой",
                    0,
                    2
                )
            )
            workRepository.addWork(Work(0, "Благоустройство двора", "26.07.2006", "СК БРИЗ", 1, 2))
            workRepository.addWork(
                Work(
                    0,
                    "Замена фонарных столбов",
                    "10.08.2017",
                    "СК БРИЗ",
                    0,
                    2
                )
            )
            workRepository.addWork(
                Work(
                    0,
                    "Ремонт дорожного покрытия",
                    "1.08.2017",
                    "Унистрой",
                    1,
                    2
                )
            )
            workRepository.addWork(
                Work(
                    0,
                    "Плановый снос здания",
                    "1.08.2027",
                    "УК \"Пжкх\"",
                    0,
                    0
                )
            )
        }
    }
}