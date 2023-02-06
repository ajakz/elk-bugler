// UserId for ProjectCanary user. In the real world this should be in a token after login
export const userId = 'e81c4664-6134-47af-b45b-c4910cbea281';

export const fetchAllElkers = async () => {
    let results;

    // Some example data:

    // results = [
    //     {
    //         elkerId: '1910672c-b26f-4ab5-9804-1b0f6ea504ac',
    //         username: 'Trustwell',
    //         following: true
    //     },
    //     {
    //         elkerId: '8a7ad5e8-4d44-4778-a9d2-03db6b813e98',
    //         username: 'Wild Goats',
    //         following: false
    //     },
    //     {
    //         id: 'e5d288fb-fe61-47b9-9680-308970bc5e4a',
    //         username: 'Lone Tusk',
    //         following: false
    //     }
    // ];

    // fetch('localhost/api/elkers?viewFor=${userId}')
    //     .then(response => response.json())
    //     .then(json => results = json)
    //     .catch(error => console.log(error));    
    

    await fetch(`http://localhost:8080/api/v1/elkers?userId=${userId}`)
        .then(response => {
            results = response.json();
        })
        .catch(error => console.log(error)); 

    return results;
};


export const fetchTimeline = async () => {
    let results = [];

    // Some example data:

    // results = [
    //     {
    //         bugleId: "e4a344ca-4902-4b95-9157-710d7ba5a140",
    //         elkerId: "e81c4664-6134-47af-b45b-c4910cbea281",
    //         username: "ProjectCanary",
    //         timestamp: "2022-09-22T21:49:12Z",
    //         content: "Trustwell Certification"
    //     },
    //     {
    //         bugleId: "e5897753-2cce-4da6-8c79-f268dfe69dbe",
    //         elkerId: "e81c4664-6134-47af-b45b-c4910cbea281",
    //         username: "ProjectCanary",
    //         timestamp: "2022-09-22T21:25:00Z",
    //         content: "Continous Monitoring"
    //     },
    //     {
    //         bugleId: "95282981-0526-4e04-a7ba-bb6866e347e9",
    //         elkerId: "e81c4664-6134-47af-b45b-c4910cbea281",
    //         username: "ProjectCanary",
    //         timestamp: "2022-09-22T21:00:12Z",
    //         content: "Responsibily Sourced GaS!"
    //     }
    // ]

    await fetch(`http://localhost:8080/api/v1/timeline?userId=${userId}`)
        .then(response => {
            results = response.json();
        })
        .catch(error => console.log(error)); 

    return results;
};


export const publishBugle = async function(bugle) {
    await fetch('http://localhost:8080/api/v1/bugle', {
        method: 'POST',
        body: JSON.stringify({
            elkerId: userId,
            content: bugle
        }),
        headers: {
            'Content-type': 'application/json'
        }
    })
        .catch(error => console.log(error));  
};


export const follow = async function (friendId) {
    await fetch('http://localhost:8080/api/v1/follow', {
        method: 'POST',
        body: JSON.stringify({
            myId: userId,
            theirId: friendId
        }),
        headers: {
            'Content-type': 'application/json'
        }
    })
        .catch(error => console.log(error)); 
};


export const unfollow = async function (friendId) {
    await fetch('http://localhost:8080/api/v1/unfollow', {
        method: 'POST',
        body: JSON.stringify({
            myId: userId,
            theirId: friendId
        }),
        headers: {
            'Content-type': 'application/json'
        }
    })
        .catch(error => console.log(error)); 
};
