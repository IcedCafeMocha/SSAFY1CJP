<template>
    <div>
      <div id="cal-heatmap"></div>
      <v-btn @click="recordAttendance" color="primary">출석하기</v-btn>
    </div>
  </template>
  <script setup>
  import { ref, onMounted } from 'vue';
  import CalHeatmap from 'cal-heatmap';
  import 'cal-heatmap/cal-heatmap.css';
  import axiosInstance from '@/utils/interceptor';
  import { useUserStore } from '@/stores/user';
  import Tooltip from 'cal-heatmap/plugins/Tooltip';
  // Store에서 사용자 정보를 가져옴
  const userStore = useUserStore();
  const user = ref(null);
  // 30일 이전의 날짜를 구하는 함수
  const getPastDate = (days) => {
    const date = new Date();
    date.setDate(date.getDate() - days);
    return date;
  };
  const loadAttendanceData = async () => {
    try {
      const response = await axiosInstance.get(`http://localhost:8080/user/attendance/${user.value.id}`);
      const attendanceData = response.data;
      // 데이터를 cal-heatmap 형식으로 변환
      const preformattedData = attendanceData.map((item,) => {
        
        return {
          date: item.dateString.split('T')[0], // 'YYYY-MM-DD' 형식으로 변환
          value: 1 // 출석 횟수 (필요에 따라 수정 가능)
        }
      }
    );
    
    const formattedData = preformattedData.reduce((accumulator, current) => {
      // accumulator에서 현재 날짜를 찾아서 찾으면 value를 더함
      let found = accumulator.find(item => item.date === current.date);
      if (found) {
        found.value += current.value; // 이미 있는 날짜면 value만 더함
      } else {
        accumulator.push({ ...current }); // 새로운 날짜면 객체를 배열에 추가
      }
      return accumulator;
    }, []);
    
    
   
      // 캘린더 히트맵 렌더링
      const cal = new CalHeatmap();
      cal.paint({
        itemSelector: '#cal-heatmap',
        domain: {
          type: 'month',
        },
        subDomain: {
          type: 'day',
        },
        data: {
          source: formattedData,
          x: 'date',
          y: 'value',
          groupY: 'sum'
        },
        date: {
          start: getPastDate(140), // 30일 이전의 날짜로 설정
          min: getPastDate(140),
          max: new Date(),
        },
        range: 5,
        legend: [2, 4, 6, 8, 10],
      },
      [
      [
        Tooltip,
        {
          text: function (date, value, dayjsDate) {
            return (
              (value ? '출석 완료!!' : 'No data') + ' on ' + dayjsDate.format('LL')
            );
          },
        },
      ],
    ]
    );
    } catch (error) {
      console.error('Error fetching attendance data:', error);
    }
  };
  const recordAttendance = async () => {
    try {
      const attendance = {
        userId: user.value.id,
        date: new Date().toISOString() // 현재 날짜 및 시간
      };
      console.log(attendance.userId);
      await axiosInstance.post('http://localhost:8080/user/attendance', attendance);
    } catch (error) {
      console.error('Error recording attendance:', error);
    }
  };
  onMounted(async () => {
    user.value = await userStore.getUserInfoFromToken();

    await loadAttendanceData();
  });
  </script>
  <style>
  button {
    padding: 5px 10px;
    background-color: #007BFF;
    border: none;
    cursor: pointer;
  }
  </style>