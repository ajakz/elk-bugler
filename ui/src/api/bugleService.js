// UserId for ProjectCanary user. In the real world this should be in a token after login
export const userId = 'e81c4664-6134-47af-b45b-c4910cbea281';

export const fetchAllElkers = () => {
    let results;

    results = [
        {
            id: '1910672c-b26f-4ab5-9804-1b0f6ea504ac',
            username: 'Trustwell',
            following: true
            // following: []
        },
        {
            id: '8a7ad5e8-4d44-4778-a9d2-03db6b813e98',
            username: 'Wild Goats',
            following: false
            // following: ['e81c4664-6134-47af-b45b-c4910cbea281','1910672c-b26f-4ab5-9804-1b0f6ea504ac','e5d288fb-fe61-47b9-9680-308970bc5e4a']
        },
        {
            id: 'e5d288fb-fe61-47b9-9680-308970bc5e4a',
            username: 'Lone Tusk',
            following: false
            // following: []
        },
        {
            id: 'e81c4664-6134-47af-b45b-c4910cbea281',
            username: 'ProjectCanary',
            following: true
            // following: ['1910672c-b26f-4ab5-9804-1b0f6ea504ac']
            // FIXME: don't return self from service
        }
    ];

    // fetch('localhost/api/elkers?viewFor=${userId}')
    //     .then(response => response.json())
    //     .then(json => results = json)
    //     .catch(error => console.log(error));
    return results;
};


export const fetchTimeline = () => {
    let results;

    results = [
        {
            id: '5e21470a-1cae-42bb-b611-827cf978e9c9',
            timestamp: '2023-01-05 01:49:04-07',
            userId: 'ProjectCanary',
            username: 'ProjectCanary',
            content: 'abc123'
        },
        {
            id: '5e21470a-1cae-42bb-b611-827cf978e9c7',
            timestamp: '2023-02-05 01:49:04-07',
            userId: 'ProjectCanary',
            username: 'ProjectCanary',
            content: 'hats off'
        },
        {
            id: '5e21470a-1cae-42bb-b611-827cf978e9c8',
            timestamp: '2023-01-25 01:49:04-07',
            userId: 'ProjectCanary',
            username: 'ProjectCanary',
            content: 'hello world'
        }
    ]

    // fetch(`localhost/api/timeline?viewFor=${userId}`)
    //     .then(response => response.json())
    //     .then(json => results = json)
    //     .catch(error => console.log(error));
    return results;
};


export const publishBugle = function(bugle) {
    console.log(`POSTed ${bugle}`)
    // fetch(`localhost/api/bugles`, {
    //     method: 'POST',
    //     body: JSON.stringify({
    //         userId: {userId},
    //         content: {bugle}
    //     }),
    //     headers: {
    //         'Content-type': 'application/json; charset=UTF-8'
    //     }
    // })
    //     .then(response => response.json())
    //     .then(json => console.log(json))
    //     .catch(error => console.log(error));
};


export const follow = function (friendId) {
    console.log(`Following ${friendId}`);
    // fetch(`localhost/api/elkers`, {
    //     method: 'POST',
    //     body: JSON.stringify({
    //         userId: {userId},
    //         friendId: {friendId},
    //         action: 'ADD'
    //     }),
    //     headers: {
    //         'Content-type': 'application/json; charset=UTF-8'
    //     }
    // })
    //     .then(response => response.json())
    //     .then(json => console.log(json))
    //     .catch(error => console.log(error));
};


export const unfollow = function (friendId) {
    console.log(`Stopped following ${friendId}`);
    // fetch(`localhost/api/elkers`, {
    //     method: 'POST',
    //     body: JSON.stringify({
    //         userId: {userId},
    //         friendId: {friendId},
    //         action: 'REMOVE'
    //     }),
    //     headers: {
    //         'Content-type': 'application/json; charset=UTF-8'
    //     }
    // })
    //     .then(response => response.json())
    //     .then(json => console.log(json))
    //     .catch(error => console.log(error));
};