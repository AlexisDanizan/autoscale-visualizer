/**
 * 
 */
package visualizer.source;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import visualizer.structure.IStructure;

/**
 * @author Roland
 *
 */
public class FileSource implements ISource {

	private ArrayList<String> topologies;
	private ArrayList<String> variations;
	private ArrayList<String> rootDirectories;
	private ArrayList<String> datasetDirectories;
	
	private static final String TOPOLOGY_INPUT = "topology_input";
	private static final String TOPOLOGY_THROUGHPUT = "topology_throughput";
	private static final String TOPOLOGY_DEPHASE = "topology_dephase";
	private static final String TOPOLOGY_LATENCY = "topology_latency";
	private static final String TOPOLOGY_NBEXEC = "topology_nb_executors";
	private static final String TOPOLOGY_NBSUPER = "topology_nb_supervisors";
	private static final String TOPOLOGY_NBWORK = "topology_nb_workers";
	private static final String TOPOLOGY_STATUS = "topology_status";
	private static final String TOPOLOGY_TRAFFIC = "topology_traffic";
	private static final String TOPOLOGY_REBALANCING = "topology_rebalancing";
	private static final String TOPOLOGY_LOAD = "topology_load";
	private static final String BOLT_INPUT = "bolt_input";
	private static final String BOLT_EXEC = "bolt_processed";
	private static final String BOLT_OUTPUT = "bolt_output";
	private static final String BOLT_LATENCY = "bolt_latency";
	private static final String BOLT_CAPACITY = "bolt_capacity";
	private static final String BOLT_ACTIVITY = "bolt_activity";
	private static final String BOLT_LOAD = "bolt_load";
	
	private static final String CAT_TOPOLOGY = "topology";
	private static final String CAT_BOLT = "bolts";
	
	private static final Logger logger = Logger.getLogger("FileSource");
	
	public FileSource(ArrayList<String> topologies, ArrayList<Integer> varCodes) {
		this.topologies = topologies;
		this.variations = new ArrayList<>();
		for(int i = 0; i < varCodes.size(); i++){
			Integer varCode = varCodes.get(i);
			switch(varCode){
			case(1): this.variations.add("linear_increase");
			break;
			case(2): this.variations.add("scale_increase");
			break;
			case(3): this.variations.add("exponential_increase");
			break;
			case(4): this.variations.add("logarithmic_increase");
			break;
			case(5): this.variations.add("linear_decrease");
			break;
			case(6): this.variations.add("scale_decrease");
			break;
			case(7): this.variations.add("exponential_decrease");
			break;
			case(8): this.variations.add("all_variations");
			break;
			case(9): this.variations.add("no_variation");
			break;
			}
		}
		this.rootDirectories = new ArrayList<>();
		this.datasetDirectories = new ArrayList<>();
		for(int i = 0; i < topologies.size(); i++){
			this.rootDirectories.add(this.topologies.get(i) + "_" + this.variations.get(0));
			this.datasetDirectories.add(this.rootDirectories.get(i) + "/datasets");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyInput()
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyInput() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyInput = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_INPUT + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyInput)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyInput, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve " + this.topologies.get(i) + " inputs because " + e);
				}
			}
		}
		return alldata;
		
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyThroughput()
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyThroughput() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyThroughput = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_THROUGHPUT + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyThroughput)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyThroughput, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " throughput because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyLosses()
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyDephase() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyLosses = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_DEPHASE + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyLosses)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data2 = (ArrayList<String>) Files.readAllLines(topologyLosses, Charset.defaultCharset());
					for(int j = 1; j < data2.size(); j++){
						String[] line = data2.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " dephases because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyLatency()
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyLatency() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyLatency = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_LATENCY + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyLatency)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyLatency, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " latency because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyNbExecutors()
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyNbExecutors() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyNbExec = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_NBEXEC + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyNbExec)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyNbExec, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " executors because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyNbSupervisors()
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyNbSupervisors() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyNbSup = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_NBSUPER + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyNbSup)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyNbSup, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " supervisors because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyNbWorkers()
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyNbWorkers() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyNbWorkers = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_NBWORK + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyNbWorkers)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyNbWorkers, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " workers because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyStatus()
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyStatus() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyStatus = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_STATUS + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyStatus)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyStatus, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " status because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getTopologyTraffic(visualizer.structure.IStructure)
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyTraffic(IStructure structure) {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyTraffic = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_TRAFFIC + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyTraffic)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyTraffic, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " traffic because " + e);
				}
			}
		}
		return alldata;
	}

	@Override
	public HashMap<String, HashMap<String, HashMap<Integer, Double>>> getTopologyRebalancing(IStructure structure) {
		HashMap<String, HashMap<String, HashMap<Integer, Double>>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyScales = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_REBALANCING + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyScales)){
				try {
					HashMap<String, HashMap<Integer, Double>> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyScales, Charset.defaultCharset());

					ArrayList<String> bolts = structure.getBolts();
					for(String bolt : bolts){
						HashMap<Integer, Double> componentInfo = new HashMap<>();
						for(int j = 1; j < data.size(); j++){
							String[] line = data.get(j).split(";");
							Integer timestamp = Integer.parseInt(line[0]);
							String[] actions = line[1].split(":");
							int nbActions = actions.length;

							for(int k = 0; k < nbActions; k++){
								String[] action = actions[k].split("@");
								String component = action[0];
								Double nbExecutors = Double.parseDouble(action[1]);
								if(component.equalsIgnoreCase(bolt)){
									componentInfo.put(timestamp, nbExecutors);
								}
							}
						}
						dataset.put(bolt, componentInfo);
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " scaling actions because " + e);
				}
			}
		}
		return alldata;
	}
	
	@Override
	public HashMap<String, HashMap<Integer, Double>> getTopologyLoads() {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path topologyLoads = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_TOPOLOGY 
					+ "/_" + TOPOLOGY_LOAD + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(topologyLoads)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(topologyLoads, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve "+ this.topologies.get(i) + " loads because " + e);
				}
			}
		}
		return alldata;
	}
	
	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getBoltInput(java.lang.String, visualizer.structure.IStructure)
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getBoltInput(String component, IStructure structure) {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path boltInput = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_BOLT + "/" 
					 + component + "_" + BOLT_INPUT + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(boltInput)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(boltInput, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve " + this.topologies.get(i) + "." + component + " input because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getBoltExecuted(java.lang.String)
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getBoltExecuted(String component) {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path boltExec = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_BOLT + "/" 
					 + component + "_" + BOLT_EXEC + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(boltExec)){
				try {
					HashMap<Integer, Double> dataset2 = new HashMap<>();
					ArrayList<String> data2 = (ArrayList<String>) Files.readAllLines(boltExec, Charset.defaultCharset());
					for(int j = 1; j < data2.size(); j++){
						String[] line = data2.get(j).split(";");
						dataset2.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset2);
				} catch (IOException e) {
					logger.severe("Unable to retrieve " + this.topologies.get(i) + "." + component + " executed tuples because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getBoltOutputs(java.lang.String)
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getBoltOutputs(String component) {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path boltOutput = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_BOLT + "/" 
					 + component + "_" + BOLT_OUTPUT + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(boltOutput)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(boltOutput, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve " + this.topologies.get(i) + "." + component + " output because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getBoltLatency(java.lang.String)
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getBoltLatency(String component) {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path boltLatency = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_BOLT + "/" 
					 + component + "_" + BOLT_LATENCY + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(boltLatency)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(boltLatency, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve " + this.topologies.get(i) + "." + component + " latency because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getBoltProcessingRate(java.lang.String)
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getBoltProcessingRate(String component) {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path boltInput = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_BOLT + "/" 
					 + component + "_" + BOLT_CAPACITY + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(boltInput)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(boltInput, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve " + this.topologies.get(i) + "." + component + " processing rate because " + e);
				}
			}
		}
		return alldata;
	}

	/* (non-Javadoc)
	 * @see visualizer.source.ISource#getBoltEPR(java.lang.String)
	 */
	@Override
	public HashMap<String, HashMap<Integer, Double>> getBoltCR(String component) {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path boltCR = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_BOLT + "/" 
					 + component + "_" + BOLT_ACTIVITY + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(boltCR)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(boltCR, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve " + this.topologies.get(i) + "." + component + " cr because " + e);
				}
			}
		}
		return alldata;
	}

	@Override
	public HashMap<String, HashMap<Integer, Double>> getBoltPL(String component) {
		HashMap<String, HashMap<Integer, Double>> alldata = new HashMap<>();
		int nbTopologies = topologies.size();
		for(int i = 0; i < nbTopologies; i++){
			Path boltPL = Paths.get(this.datasetDirectories.get(i) + "/" + CAT_BOLT + "/" 
					 + component + "_" + BOLT_LOAD + "_" + this.rootDirectories.get(i) + ".csv");
			if(Files.exists(boltPL)){
				try {
					HashMap<Integer, Double> dataset = new HashMap<>();
					ArrayList<String> data = (ArrayList<String>) Files.readAllLines(boltPL, Charset.defaultCharset());
					for(int j = 1; j < data.size(); j++){
						String[] line = data.get(j).split(";");
						dataset.put(Integer.parseInt(line[0]), Double.parseDouble(line[1]));
					}
					alldata.put(this.topologies.get(i), dataset);
				} catch (IOException e) {
					logger.severe("Unable to retrieve " + this.topologies.get(i) + "." + component + " pl because " + e);
				}
			}
		}
		return alldata;
	}


	
}