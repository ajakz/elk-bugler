import React, { useState, useEffect } from 'react';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import RefreshIcon from '@mui/icons-material/Refresh';
import RecordVoiceOverIcon from '@mui/icons-material/RecordVoiceOver';
import Paper from '@mui/material/Paper';
import Divider from '@mui/material/Divider';
import { userId, fetchTimeline, fetchAllElkers, publishBugle, unfollow, follow } from './api/bugleService.js';


export default function App() {

  const [userBugle, setUserBugle] = useState('');

  const handleTextFieldChange = e => {
      setUserBugle(e.target.value);
  };

  const bugleAudioPath = () => {
      return `/elk-bugle-sounds-0${Math.floor(Math.random() * 5) + 1}.mp3`;
  }

  let audio = new Audio();

  const bugleAudio = () => {
      if(audio.currentSrc === "" || audio.ended) {
          audio.src = bugleAudioPath();
          audio.load();
          audio.play();
      }
      else if(audio.paused && !audio.ended) {
          audio.play();
      } 
      else {
          audio.pause();
      }
  }

  const [elkerList, setElkerList] = useState([]);

  const refreshElkers = async () => {
      const elkers = await fetchAllElkers();
      setElkerList(elkers);
  }

  // On mount, GET user list. Passing empty array, this runs only once at mounting -- refreshing
  // the list is then done only by button click to avoid fetching data more than necessary
  useEffect(() => {
      refreshElkers();
  }, []);

  const [bugleList, setBugleList] = useState([]);
  
  const refreshTimeline = async () => {
      const timeline = await fetchTimeline();
      setBugleList(timeline);
  }

  const postBugle = async () => {
      await publishBugle(userBugle);
      setUserBugle("");
      await refreshTimeline();
  }

  // On mount, GET user's timeline. Passing empty array, this runs only once at mounting -- refreshing
  // the list is then done only by button click to avoid fetching data more than necessary
  useEffect(() => {
      refreshTimeline();
  }, []);

  const updateFollowing = async function(userId, following) {
       if(following) {
          await unfollow(userId);
       }  
       else {
          await follow(userId);
       } 
       await refreshElkers();
       await refreshTimeline();
  }

  const bugleCard = function (bugleData) {
      return (
          <React.Fragment>
              <Paper id={bugleData.id} elevation={3} sx={{ p: 1 }}>
                  <Typography variant="body1">{bugleData.content}</Typography>
                  <Typography variant="body2" align='right'>{bugleData.username} at {bugleData.timestamp}</Typography>
              </Paper>
          </React.Fragment>
      );
  }

  const elkerCard = function (elkerData) {
      const buttonText = elkerData.following ? 'Unfollow' : 'Follow';

      return (
          <React.Fragment>
          <Paper id={elkerData.id} elevation={3} sx={{ p: 1 }}>
              <Typography variant="h5">{elkerData.username}</Typography>
              <Button variant="text" onClick={() => updateFollowing(elkerData.elkerId, elkerData.following)}>{buttonText}</Button>
          </Paper>
      </React.Fragment>
      );
  }
  
  const bugles = bugleList.map(bugle => bugleCard(bugle));

  const elkers = elkerList.map(elker => elkerCard(elker));

  return (
    <Container maxWidth="md">
      <Typography variant="h4" component="h1" sx={{ mt: 6, mb: 3 }}>
        Elker: Bugle Cause You Gotta
      </Typography>
      <Box component="form" sx={{ my: 4 }} id="bugleMaker">
            <TextField fullWidth label="New Bugle" id="userInput" value={userBugle} onChange={handleTextFieldChange}></TextField>
            <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 2 }}>
                <Button variant="outlined" startIcon={<RecordVoiceOverIcon />} onClick={bugleAudio}>Make Some Noise</Button>
                <Button variant="contained" onClick={postBugle}>Publish</Button>
            </Stack>
        </Box>
        <Box sx={{ my: 4 }} id="elkersAndTimeline">
            <Divider sx={{ my: 2}}>ELKERS</Divider>
            <Stack direction="column" justifyContent="center" spacing={2} sx={{ p: 2, border: 'thick double grey' }}>
                {elkers}
            </Stack>
            <Divider sx={{ mt: 4}}>TIMELINE</Divider>
            <Box textAlign="center">
                <Button 
                    variant="contained" 
                    startIcon={<RefreshIcon />} 
                    sx={{ mt: 2, mb: 3 }}
                    onClick={refreshTimeline}>
                        Refresh
                </Button></Box>
            <Stack direction="column" justifyContent="center" alignItems="stretch" spacing={2} sx={{ p: 2, border: 'thick double grey' }}>
                {bugles}
            </Stack>
        </Box>
    </Container>
  );
}
