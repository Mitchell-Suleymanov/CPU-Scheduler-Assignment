public class Process 
{
	private int id;
	private int arrivalTime;
	private int cpuBurst;
	
	Process()
	{
		id = 0;
		arrivalTime =0;
		cpuBurst = 0;
	}
	
	Process(int id, int arrivalTime, int cpuBurst){
	this.id = id;
	this.arrivalTime = arrivalTime;
	this.cpuBurst = cpuBurst;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getCpuBurst() {
		return cpuBurst;
	}
	
	public void setCpuBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}

}

