#!/usr/bin/env python

from random import randint
from pymongo import MongoClient, ASCENDING
from datetime import datetime, timedelta
import pytz

eastern = pytz.timezone('US/Eastern')
central = pytz.timezone('US/Central')
pacific = pytz.timezone('US/Pacific')
mountain = pytz.timezone('US/Mountain')
alaskan = pytz.timezone('US/Alaska')
hawaii = pytz.timezone('US/Hawaii')

source = (
            (("DFW", central), ("MSP", central), 3, '173JL'),
            (("DFW", central), ("LAX", pacific), 4, '5BDAQ'),
            (("ORD", central), ("LAX", pacific), 4, 'E14F9'),
            (("SEA", pacific), ("JFK", eastern), 4, 'AZYF7'),
            (("MCI", central), ("LAX", pacific), 2, 'LR700'),
            (("ATL", eastern), ("SEA", pacific), 5, 'BS459'),
            (("FLG", mountain), ("SEA", pacific), 4, 'GS4F9'),
            (("ANC", alaskan), ("DFW", pacific), 7, 'AS4K9'),
            (("HNL", hawaii), ("LAX", pacific), 10, 'HLM8T')
    )

airlines = ['B6', '9W', 'SG', 'AA']
batch_size = 100

def get_utc_datetime(date, time, utc_offset):
    return date + timedelta(minutes=time - utc_offset)


def compose_leg(leg_id, flight_number, version):
    # print('composing leg for id: ' + str(leg_id))
    index = randint(0, len(source) - 1 )
    depTz = source[index][0][1]
    arvTz = source[index][1][1]

    today = datetime.today()

    zflightDate = datetime(2018, randint(today.month, 12), randint(1, 28), randint(0,23), 0, 0, 0, tzinfo=pytz.utc)
    zScheduleArrivalTime = zflightDate + timedelta(hours= source[index][2])
    leg = {
        '_id':{
        'leg_id': leg_id,
         'version' : version
        },
        'flightLegId' : {
            'flightNumber': str(flight_number) ,
            'departureAirport' : source[index][0][0],
            'arrivalAirport': source[index][1][0],
            'flightDate': zflightDate.astimezone(depTz).isoformat(),
            'airlineCode':airlines[randint(0, len(airlines) - 1)]
        },
        'flightTimes':{
            'scheduleDepartureTime' : zflightDate.astimezone(depTz).isoformat(),
            'scheduleArrivalTime':zScheduleArrivalTime.astimezone(arvTz).isoformat(),
            'latestDepartureTime':zflightDate.astimezone(depTz).isoformat(),
            'latestArrivalTime':zScheduleArrivalTime.astimezone(arvTz).isoformat()
        },
        'registration':source[index][3],
        'status':'SCH',
        'serviceType':'J'
    }

    return leg



def load_legs(db, limit):
    print('creating and inserting legs in batches of: ' + str(batch_size))
    legs = []
    total_legs_loaded = 0
    for index in range(1, limit + 1):
        leg = compose_leg(leg_id=index, flight_number=index, version=1)
        legs.append(leg)
        if len(legs) % batch_size == 0:
            total_legs_loaded += len(legs)
            insert_legs(db, legs)
            legs = []
    print("# of total legs inserted in db: " + str(total_legs_loaded))

def insert_legs(db, legs):
    db.flight_leg.insert_many(legs)
#    print("# of legs inserted: " + str(len(legs)))


if __name__ == '__main__':
    print('creating database connection')
    client = MongoClient("mongodb://localhost:27017/ops_db")
    # client = MongoClient("mongodb://mongo-schedule-service.192.168.64.13.nip.io:27017/ops_db")
    # client = MongoClient("localhost", 27017)
    ops_db = client.ops_db

    print('loading flight legs')
    load_legs(db=ops_db, limit=1000)

    print("creating index")
    ops_db.flight_leg.create_index([('flightLegId.airlineCode', ASCENDING), ('flightLegId.departureAirport', ASCENDING)])
