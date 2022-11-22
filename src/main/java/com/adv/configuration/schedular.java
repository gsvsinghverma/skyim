package com.adv.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adv.model.CpuUsage;
import com.adv.model.MemoryUsage;
import com.adv.repository.CpuUsageRepository;
import com.adv.repository.MemoryUsageRepository;
import com.adv.util.Util;

@Component
public class schedular {

	Logger LOGGER = LoggerFactory.getLogger(schedular.class.getName());
	@Autowired
	MemoryUsageRepository memoryUsageRepository;

	@Autowired
	CpuUsageRepository cpuUsageRepository;

	@Scheduled(cron = "0 0 0/1 * * ?")
	public void cronJobSch() {

		String s;
		Process p;
		try {
			if (Util.isUnix()) {
				p = Runtime.getRuntime().exec("cat /proc/meminfo");

				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				int count = 0;
				Long totalmemory = null;
				Long MemFree = null;
				Long Buffers = null;
				Long Cached = null;
				Long Slab = null;
				Long usedMemory = null;
				Long feeMemory = null;

				while ((s = br.readLine()) != null) {
					String[] memvalue = s.split("\\s");

					for (int j = 0; j < memvalue.length; j++) {
						String value = memvalue[j].trim();

						String regex = "[0-9]+";

						boolean matches = value.matches(regex);

						if (matches == true && count == 0) {
							totalmemory = (Long.parseLong(value)) / 1024;

						}
						if (matches == true && count == 1) {
							MemFree = (Long.parseLong(value)) / 1024;

						}

						if (matches == true && count == 3) {
							Buffers = (Long.parseLong(value)) / 1024;

						}

						if (matches == true && count == 4) {
							Cached = (Long.parseLong(value)) / 1024;

						}

						if (matches == true && count == 21) {
							Slab = (Long.parseLong(value)) / 1024;

						}

					}
					count++;
				}

				usedMemory = totalmemory - MemFree - Buffers - Cached - Slab;
				feeMemory = totalmemory - usedMemory;

				MemoryUsage memory = new MemoryUsage();
				memory.setFreePhysicalMemorySize(feeMemory);
				memory.setTotalPhysicalMemorySize(totalmemory);
				memory.setUsagePhysicalMemorySize(usedMemory);
				Timestamp currentDT = new Timestamp(System.currentTimeMillis());
				memory.setCreationDate(currentDT);
				memoryUsageRepository.save(memory);
				ZonedDateTime zonedDateTime = currentDT.toInstant().atZone(ZoneId.of("UTC"));
				Timestamp newdate = Timestamp.from(zonedDateTime.plus(-30, ChronoUnit.DAYS).toInstant());

				memoryUsageRepository.removeOlderThan(newdate);
				p.waitFor();

				p.destroy();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		OperatingSystemMXBean omx = ManagementFactory.getOperatingSystemMXBean();
		com.sun.management.OperatingSystemMXBean osMxBean = null;

		if (omx instanceof com.sun.management.OperatingSystemMXBean) {
			osMxBean = (com.sun.management.OperatingSystemMXBean) omx;
		}
		CpuUsage cpuUsage = new CpuUsage();
		if (osMxBean != null) {

			double cpu1 = osMxBean.getProcessCpuLoad();
			DecimalFormat df = new DecimalFormat("0.00");
			cpuUsage.setProcessCpuLoad(df.format(cpu1));

			long cpu2 = osMxBean.getProcessCpuTime();
			cpuUsage.setProcessCpuTime(convertTimeStampToStringDate(cpu2));
			double cpu3 = osMxBean.getSystemCpuLoad();
			cpuUsage.setSystemCpuLoad(df.format(cpu3));
		
			Timestamp currentDT = new Timestamp(System.currentTimeMillis());
			cpuUsage.setCreationDate(currentDT);
			cpuUsageRepository.save(cpuUsage);

			ZonedDateTime zonedDateTime = currentDT.toInstant().atZone(ZoneId.of("UTC"));
			Timestamp newdate = Timestamp.from(zonedDateTime.plus(-30, ChronoUnit.DAYS).toInstant());
			cpuUsageRepository.removeOlderThan(newdate);

		}

	}

	public static String convertTimeStampToStringDate(long timeStamp) {
		if (timeStamp != 0L) {
			Date currentDate = new Date(timeStamp);
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			String newDate = df.format(currentDate);
			return newDate;
		} else {
			return "";
		}
	}

}
