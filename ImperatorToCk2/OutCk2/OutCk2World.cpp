#include "OutCk2World.h"
#include <filesystem>
#include <fstream>
#include <string>



namespace Ck2World
{

void outputModFile(const std::string& outputName);
void createModFolder(const std::string& outputName);

} // namespace Ck2World


void Ck2World::outWorld(const World& world)
{
	const auto& outputName = world.getModName();
	createModFolder(outputName);
	outputModFile(outputName);
}


void Ck2World::outputModFile(const std::string& outputName)
{
	std::ofstream modFile{"output/" + outputName + ".mod"};
	modFile << "name = \"Converted - " << outputName << "\"\n";
	modFile << "path = \"mod/" << outputName << "\"";
	modFile.close();
}


void Ck2World::createModFolder(const std::string& outputName)
{
	const std::filesystem::path modPath{"output/" + outputName};
	create_directories(modPath);
}